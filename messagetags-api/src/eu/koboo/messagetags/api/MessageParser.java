package eu.koboo.messagetags.api;

import com.hypixel.hytale.common.util.ArrayUtil;
import com.hypixel.hytale.server.core.Message;
import eu.koboo.messagetags.api.colors.LegacyColorCodes;
import eu.koboo.messagetags.api.colors.NamedColor;
import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;
import eu.koboo.messagetags.api.taghandler.types.*;
import eu.koboo.messagetags.api.variables.TagVariable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class MessageParser {

    public static final char TAG_OPEN = '<';
    public static final char TAG_CLOSE = '>';
    public static final char TAG_SLASH = '/';
    public static final char TAG_SEPARATOR = ':';
    public static final char COLOR_PREFIX = '#';
    public static final char LEGACY_AMPERSAND = '&';
    public static final char LEGACY_SECTION = '§';

    private TagHandler[] tagHandlers = new TagHandler[0];
    private long handlerLength = 0;

    private final Map<String, NamedColor> nameToColorRegistry = new HashMap<>();

    MessageParser() {
        // TagHandlers get execute based on the registration order.
        registerTagHandler(ResetTagHandler.INSTANCE);
        registerTagHandler(LineBreakTagHandler.INSTANCE);

        registerTagHandler(BoldTagHandler.INSTANCE);
        registerTagHandler(ItalicTagHandler.INSTANCE);
        registerTagHandler(UnderlineTagHandler.INSTANCE);
        registerTagHandler(MonospaceTagHandler.INSTANCE);

        registerTagHandler(LinkTagHandler.INSTANCE);
        registerTagHandler(TranslationTagHandler.INSTANCE);

        registerTagHandler(ColorTagHandler.INSTANCE);
        registerTagHandler(GradientTagHandler.INSTANCE);
        registerTagHandler(TransitionTagHandler.INSTANCE);

        registerTagHandler(DynamicColorTagHandler.INSTANCE);

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
        handlerLength = tagHandlers.length;
    }

    public void registerNamedColor(@Nonnull NamedColor namedColor) {
        nameToColorRegistry.put(namedColor.name(), namedColor);
    }

    @Nullable
    public NamedColor getNamedColorByName(@Nonnull String colorName) {
        colorName = colorName.toLowerCase(Locale.ROOT).trim();
        return nameToColorRegistry.get(colorName);
    }

    @Nonnull
    public Message parse(@Nullable String inputText, boolean strip, TagVariable... variables) {
        // null or empty, return an empty message
        if (inputText == null || inputText.isEmpty()) {
            return Message.empty();
        }
        final int inputLength = inputText.length();
        if (!hasParseableCharacter(inputText, inputLength)) {
            return Message.raw(inputText);
        }
        if (variables != null && variables.length > 0) {
        }

        final MessageBuilder state = new MessageBuilder(this, inputText, strip);

        int inputPos = 0;

        int openPos = -1;
        int closePos = -1;
        int argumentStartPos = -1;
        int argumentEndPos = -1;
        int slashPos = -1;

        boolean isTagOpen = false;
        boolean foundArgument = false;
        boolean foundSlash = false;

        int afterPreviousClosePos = 0;

        // Go through every character of the inputText
        while (inputPos < inputLength) {

            final char character = inputText.charAt(inputPos);

            if (character == TAG_OPEN && !isTagOpen) {
                isTagOpen = true;
                openPos = inputPos;
                inputPos++;
                continue;
            }

            if (character == TAG_SEPARATOR && !foundArgument && isTagOpen) {
                foundArgument = true;
                argumentStartPos = inputPos + 1;
                inputPos++;
                continue;
            }

            if (character == TAG_SLASH && !foundSlash && isTagOpen) {
                foundSlash = true;
                slashPos = inputPos;
                inputPos++;
                continue;
            }

            if (character == TAG_CLOSE && isTagOpen) {
                closePos = inputPos;
                inputPos++;
            }

            if (isTagOpen && closePos != -1) {

                int nameStartPos;
                int nameEndPos;
                TagAction action;

                if (foundSlash && slashPos == openPos + 1) {
                    // </tag>
                    action = TagAction.Close;
                    nameStartPos = openPos + 2;
                    if (foundArgument) {
                        nameEndPos = argumentStartPos - 1;
                        argumentEndPos = closePos;
                    } else {
                        nameEndPos = closePos;
                    }
                } else if (foundSlash && slashPos == closePos - 1) {
                    // <tag/>
                    action = TagAction.Directive;
                    nameStartPos = openPos + 1;
                    if (foundArgument) {
                        nameEndPos = argumentStartPos - 1;
                        argumentEndPos = closePos - 1;
                    } else {
                        nameEndPos = closePos - 1;
                    }
                } else {
                    // <tag>
                    action = TagAction.Open;
                    nameStartPos = openPos + 1;
                    if (foundArgument) {
                        nameEndPos = argumentStartPos - 1;
                        argumentEndPos = closePos;
                    } else {
                        nameEndPos = closePos;
                    }
                }

                // Flush everything before the current tag
                // Take in example this string:
                // "First<color:#ffffff>Second"
                // The part "First" gets flushed with the current style.
                // The tag now gets parsed by its handler. If the handler can't handle
                // the tag, the tag gets appended as raw text.
                flush(state, inputText, afterPreviousClosePos, openPos);

                state.updateCurrentTag(
                    nameStartPos, nameEndPos,
                    argumentStartPos, argumentEndPos,
                    action
                );

                // Found tag positions and now we process it.
                boolean wasHandled = false;

                for (int handlerIndex = 0; handlerIndex < handlerLength; handlerIndex++) {
                    TagHandler tagHandler = tagHandlers[handlerIndex];
                    boolean canHandle = tagHandler.canHandle(state, nameStartPos, nameEndPos);
                    if (!canHandle) {
                        continue;
                    }

                    // Flush everything before the current tag
                    // Take in example this string:
                    // "First<color:#ffffff>Second"
                    // The part "First" gets flushed with the current style.
                    // The tag now gets parsed by its handler. If the handler can't handle
                    // the tag, the tag gets appended as raw text.

                    wasHandled = tagHandler.handle(
                        state,
                        nameStartPos, nameEndPos,
                        argumentStartPos, argumentEndPos,
                        action
                    );
                    break;
                }

                // Check if the text was handled.
                // Unhandled text gets append as a raw message with the current styling. otherwise, we don't touch it.
                if (!wasHandled) {
                    flush(state, inputText, openPos, closePos + 1);
                }

                // Move to next, we are at our close character
                afterPreviousClosePos = inputPos;

                // Resetting after tag handling
                openPos = -1;
                closePos = -1;
                argumentStartPos = -1;
                argumentEndPos = -1;
                slashPos = -1;

                isTagOpen = false;
                foundArgument = false;
                foundSlash = false;
                continue;
            }

            // Check for '§' or '&'
            if (character == LEGACY_AMPERSAND || character == LEGACY_SECTION) {
                char legacyColorCode = inputText.charAt(inputPos + 1);
                boolean isLegacyColorCode = LegacyColorCodes.isLegacyColorCode(legacyColorCode);
                if (isLegacyColorCode) {
                    flush(state, inputText, afterPreviousClosePos, inputPos);
                    LegacyColorCodes.applyLegacyColorCode(state, legacyColorCode);

                    inputPos = inputPos + 2;
                    afterPreviousClosePos = inputPos;
                    continue;
                }
            }

            // collect raw text, token names, arguments and similar
            inputPos++;
        }

        // Flush any remaining inputText onto the currently pending message.
        flush(state, inputText, afterPreviousClosePos, inputLength);

        return state.buildRootMessage();
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

    private boolean hasParseableCharacter(String inputText, int inputLength) {
        for (int i = 0; i < inputLength; i++) {
            char character = inputText.charAt(i);
            if (character == TAG_OPEN || character == LEGACY_AMPERSAND || character == LEGACY_SECTION) {
                return true;
            }
        }
        return false;
    }

    private void flush(MessageBuilder state, String inputText, int startPos, int endPos) {
        if (startPos >= endPos) {
            return;
        }
        String unflushedContent = inputText.substring(startPos, endPos);
        state.appendStyledText(unflushedContent);
    }

    public static int createStringHashCode(String string, int start, int end) {
        int hash = 0;
        for (int i = start; i < end; i++) {
            hash = hash * 31 + string.charAt(i);
        }
        return hash;
    }
}
