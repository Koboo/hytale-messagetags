package eu.koboo.messagetags.api;

import com.hypixel.hytale.common.util.ArrayUtil;
import com.hypixel.hytale.server.core.Message;
import eu.koboo.messagetags.api.colors.NamedColor;
import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;
import eu.koboo.messagetags.api.taghandler.types.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class MessageParser {

    public static final char TAG_OPEN = '<';
    public static final char TAG_CLOSE = '>';
    public static final char TAG_SLASH = '/';
    public static final char TAG_SEPARATOR = ':';
    public static final char COLOR_PREFIX = '#';
    public static final char COLOR_AMPERSAND = '&';
    public static final char COLOR_SECTION = '§';

    private static final List<Character> DISALLOWED_COLOR_CODES = List.of(
        MessageParser.COLOR_SECTION,
        MessageParser.COLOR_AMPERSAND,
        MessageParser.COLOR_PREFIX,
        MessageParser.TAG_OPEN,
        MessageParser.TAG_CLOSE,
        MessageParser.TAG_SEPARATOR,
        MessageParser.TAG_SLASH
    );

    private final ThreadLocal<MessageBuilder> threadState =
        ThreadLocal.withInitial(MessageBuilder::new);
    private final ThreadLocal<StringCursor> threadCursor =
        ThreadLocal.withInitial(StringCursor::new);
    private TagHandler[] tagHandlers = new TagHandler[0];

    private final Map<Character, NamedColor> characterToColorRegistry = new HashMap<>();
    private final Map<String, NamedColor> nameToColorRegistry = new HashMap<>();
    private final Map<String, NamedColor> hexCodeToColorRegistry = new HashMap<>();

    MessageParser() {
        registerTagHandler(BoldTagHandler.INSTANCE);
        registerTagHandler(ColorTagHandler.INSTANCE);
        registerTagHandler(GradientTagHandler.INSTANCE);
        registerTagHandler(ItalicTagHandler.INSTANCE);
        registerTagHandler(LinkTagHandler.INSTANCE);
        registerTagHandler(MonospaceTagHandler.INSTANCE);
        registerTagHandler(LineBreakTagHandler.INSTANCE);
        registerTagHandler(ResetTagHandler.INSTANCE);
        registerTagHandler(TransitionTagHandler.INSTANCE);
        registerTagHandler(TranslationTagHandler.INSTANCE);
        registerTagHandler(UnderlinedTagHandler.INSTANCE);

        registerNamedColor(NamedColor.Black);
        registerNamedColor(NamedColor.DarkBlue);
        registerNamedColor(NamedColor.DarkGreen);
        registerNamedColor(NamedColor.DarkAqua);
        registerNamedColor(NamedColor.DarkRed);
        registerNamedColor(NamedColor.DarkPurple);
        registerNamedColor(NamedColor.Gold);
        registerNamedColor(NamedColor.Gray);
        registerNamedColor(NamedColor.DarkGray);
        registerNamedColor(NamedColor.Blue);
        registerNamedColor(NamedColor.Green);
        registerNamedColor(NamedColor.Aqua);
        registerNamedColor(NamedColor.Red);
        registerNamedColor(NamedColor.LightPurple);
        registerNamedColor(NamedColor.Yellow);
        registerNamedColor(NamedColor.White);
    }

    public void registerTagHandler(@Nonnull TagHandler tagHandler) {
        tagHandlers = ArrayUtil.append(tagHandlers, tagHandler);
    }

    public void registerNamedColor(@Nonnull NamedColor namedColor) {
        char colorCode = namedColor.colorCode;
        if (colorCode != Character.MIN_VALUE) {
            if (DISALLOWED_COLOR_CODES.contains(colorCode)) {
                throw new IllegalArgumentException("colorCode " + colorCode + " is preserved as tag token, you can't use it!");
            }
            characterToColorRegistry.put(colorCode, namedColor);
        }
        String name = namedColor.name;
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        nameToColorRegistry.put(name, namedColor);
        String hexCode = namedColor.hexCode;
        if (hexCode.isEmpty()) {
            throw new IllegalArgumentException("HexCode cannot be empty!");
        }
        if (hexCode.length() == 6) {
            throw new IllegalArgumentException("HexCode has to be prefixed with '#'");
        }
        hexCode = hexCode.toLowerCase(Locale.ROOT);
        hexCodeToColorRegistry.put(hexCode, namedColor);
    }

    @Nullable
    public NamedColor getNamedColorByChar(char colorCharacter) {
        return characterToColorRegistry.get(colorCharacter);
    }

    @Nullable
    public NamedColor getNamedColorByName(@Nonnull String colorName) {
        colorName = colorName.toLowerCase(Locale.ROOT);
        return nameToColorRegistry.get(colorName);
    }

    @Nullable
    public NamedColor getNamedColorByHexCode(@Nonnull String hexCode) {
        if (hexCode.charAt(0) != MessageParser.COLOR_PREFIX) {
            hexCode = MessageParser.COLOR_PREFIX + hexCode;
        }
        if (hexCode.length() != 7) {
            return null;
        }
        hexCode = hexCode.toLowerCase(Locale.ROOT);
        return hexCodeToColorRegistry.get(hexCode);
    }

    @Nonnull
    public Message parse(@Nullable String inputText, boolean strip) {
        // null or empty, return an empty message
        if (inputText == null || inputText.isEmpty()) {
            return Message.empty();
        }
        // No '<' nor '>', return a raw message
        if (inputText.indexOf(TAG_OPEN) == -1 || inputText.indexOf(TAG_CLOSE) == -1) {
            return Message.raw(inputText);
        }
        final int inputLength = inputText.length();
        // Fewer or only 3 characters?
        // Probably nothing we can parse.
        // the tag brackets themselves need 2 characters at minimum.
        if (inputLength <= 2) {
            return Message.raw(inputText);
        }

        final MessageBuilder state = threadState.get();
        state.init(this, inputText, strip);

        final StringCursor cursor = threadCursor.get();
        cursor.init(inputText);

        int textStartPos = 0;

        // Go through every character of the inputText
        while (cursor.hasNext()) {

            final char currentChar = cursor.currentChar();

            // Normal inputText character
            if (currentChar != TAG_OPEN) {
                cursor.nextChar();
                continue;
            }

            // This is where the tag's open bracket is located
            int tagOpenPos = cursor.currentPosition();

            // This is where the tag's closing bracket is located (if present)
            int tagClosePos = -1;
            while (cursor.hasNext()) {
                if (cursor.currentChar() == TAG_CLOSE) {
                    tagClosePos = cursor.currentPosition();
                    break;
                }
                cursor.nextChar();
            }

            // No closing bracket ('>') found,
            // so we treat the previous opening bracket ('<') as normal inputText
            if (tagClosePos == -1) {
                cursor.nextChar();
                continue;
            }

            TagAction action;
            if (cursor.charEquals(tagOpenPos + 1, TAG_SLASH)) {
                action = TagAction.Close;
            } else if (cursor.charEquals(tagClosePos - 1, TAG_SLASH)) {
                action = TagAction.Directive;
            } else {
                action = TagAction.Open;
            }

            // Define the position of where the tag name starts
            int tagNameStartPos = tagOpenPos + action.namePosPadding;
            int textEndPos = tagClosePos + 1;

            // If we reach a ':', '/' or '>'. The tags name ends there.
            // ':' indicates a argument
            // '/' indicates a directive tag
            // '>' indicates a closing tag
            int tagNameEndPos = tagNameStartPos;
            boolean hasArguments = false;
            while (tagNameEndPos < tagClosePos) {
                char nameEndToken = inputText.charAt(tagNameEndPos);
                if (nameEndToken == TAG_SEPARATOR) {
                    hasArguments = true;
                    break;
                }
                if (nameEndToken == TAG_SLASH) {
                    break;
                }
                tagNameEndPos++;
            }

            // Create the start and end position of the argument and
            // let the TagHandler decode the string according to their needs.
            final int argumentStart;
            final int argumentEnd;
            if (hasArguments) {
                argumentStart = tagNameEndPos + 1;
                if(action == TagAction.Directive) {
                    // Reduce away the '/'
                    argumentEnd = tagClosePos - 1;
                } else {
                    argumentEnd = tagClosePos;
                }
            } else {
                argumentStart = -1;
                argumentEnd = -1;
            }

            // Execute the tag handlers
            boolean wasHandled = false;
            int tagHandlerAmount = tagHandlers.length;
            for (int handlerIndex = 0; handlerIndex < tagHandlerAmount; handlerIndex++) {
                TagHandler tagHandler = tagHandlers[handlerIndex];
                if (!tagHandler.canHandle(state, tagNameStartPos, tagNameEndPos)) {
                    continue;
                }

                // Flush everything before the current tag
                // Take in example this string:
                // "First<color:#ffffff>Second"
                // The part "First" gets flushed with the current style.
                // The tag now gets parsed by its handler. If the handler can't handle
                // the tag, the tag gets appended as raw text.
                String preTagContent = cursor.subString(textStartPos, tagOpenPos);
                if (preTagContent != null && !preTagContent.isEmpty()) {
                    state.appendStyledText(preTagContent);
                }

                wasHandled = tagHandler.handle(
                    state,
                    tagNameStartPos, tagNameEndPos,
                    argumentStart, argumentEnd,
                    action
                );

                break;
            }

            // Unhandled text. Check if we have a dynamic color tag and if so, apply it.
            if (!wasHandled) {
                TagHandler dynamicColorHandler = DynamicColorTagHandler.INSTANCE;
                if(dynamicColorHandler.canHandle(state, tagNameStartPos, tagNameEndPos)) {

                    String preTagContent = cursor.subString(textStartPos, tagOpenPos);
                    if (preTagContent != null && !preTagContent.isEmpty()) {
                        state.appendStyledText(preTagContent);
                    }

                    wasHandled = dynamicColorHandler.handle(
                        state,
                        tagNameStartPos, tagNameEndPos,
                        argumentStart, argumentEnd,
                        action
                    );
                }
            }

            // Check if the text was handled.
            // Unhandled text gets append as a raw message with the current styling. otherwise, we don't touch it.
            if(!wasHandled) {
                String unhandledContent = cursor.subString(tagOpenPos, textEndPos);
                if (unhandledContent != null && !unhandledContent.isEmpty()) {
                    state.appendStyledText(unhandledContent);
                }
            }

            // Move the cursor to the next character after the closing bracket
            cursor.setPosition(textEndPos);
            // Restart the process
            textStartPos = textEndPos;
        }

        // Flush any remaining inputText onto the currently pending message.
        String unflushedContent = cursor.subString(textStartPos, cursor.length());
        if (unflushedContent != null && !unflushedContent.isEmpty()) {
            state.appendStyledText(unflushedContent);
        }

        Message message = state.buildRootMessage();

        threadState.remove();
        threadCursor.remove();

        return message;
    }

    @Nullable
    public String stripRawText(@Nullable Message message) {
        if (message == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        if (message.getRawText() != null || message.getMessageId() != null) {
            if (message.getRawText() != null) {
                builder.append(message.getRawText());
            } else {
                builder.append(message.getMessageId());
            }
        }
        for (Message child : message.getChildren()) {
            String childText = stripRawText(child);
            builder.append(childText);
        }
        return builder.toString();
    }
}
