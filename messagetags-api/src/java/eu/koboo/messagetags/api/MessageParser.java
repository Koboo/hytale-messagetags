package eu.koboo.messagetags.api;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.console.ConsoleSender;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;
import eu.koboo.messagetags.api.taghandler.types.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public final class MessageParser {

    public static final char TAG_OPEN = '<';
    public static final char TAG_CLOSE = '>';
    public static final char TAG_SLASH = '/';
    public static final char TAG_SEPARATOR = ':';
    public static final char COLOR_PREFIX = '#';
    public static final char COLOR_AMPERSAND = '&';
    public static final char COLOR_SECTION = '§';

    private final List<TagHandler> tagHandlers = new ArrayList<>();

    MessageParser() {
        registerTagHandler(BoldTagHandler.INSTANCE);
        registerTagHandler(ColorTagHandler.INSTANCE);
        registerTagHandler(DynamicColorTagHandler.INSTANCE);
        registerTagHandler(GradientTagHandler.INSTANCE);
        registerTagHandler(ItalicTagHandler.INSTANCE);
        registerTagHandler(LinkTagHandler.INSTANCE);
        registerTagHandler(MonospacedTagHandler.INSTANCE);
        registerTagHandler(LineBreakTagHandler.INSTANCE);
        registerTagHandler(ResetTagHandler.INSTANCE);
        registerTagHandler(TransitionTagHandler.INSTANCE);
        registerTagHandler(TranslationTagHandler.INSTANCE);
        registerTagHandler(UnderlinedTagHandler.INSTANCE);
    }

    public void registerTagHandler(@Nonnull TagHandler tagHandler) {
        tagHandlers.add(tagHandler);
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

        final MessageBuilder state = new MessageBuilder(strip);
        int cursor = 0;
        int textStartIndex = 0;

        // Go through every character of the inputText
        while (cursor < inputLength) {

            final char currentChar = inputText.charAt(cursor);

            // Normal inputText character
            if (currentChar != TAG_OPEN) {
                cursor++;
                continue;
            }

            // This is where the tag's open bracket is located
            final int tagOpenBracketIndex = cursor;

            // This is where the tag's closing bracket is located (if present)
            final int tagCloseBracketIndex = findTagEnd(inputText, cursor + 1);

            // No closing bracket ('>') found,
            // so we treat the previous opening bracket ('<') as normal inputText
            if (tagCloseBracketIndex == -1) {
                cursor++;
                continue;
            }

            TagAction action;
            int tagNameStartPadding = 1;
            if (inputText.charAt(tagOpenBracketIndex + 1) == TAG_SLASH) {
                // </TAG_NAME>
                action = TagAction.Close;
                // add padding for the "/" in front of the tag name
                tagNameStartPadding++;
            } else if (inputText.charAt(tagCloseBracketIndex - 1) == TAG_SLASH) {
                // <TAG_NAME/>
                action = TagAction.Directive;
            } else {
                // <TAG_NAME>
                action = TagAction.Open;
            }

            // Define the position of where the tag name starts
            final int tagNameStartIndex = tagOpenBracketIndex + tagNameStartPadding;
            final int textEndIndex = tagCloseBracketIndex + 1;

            // If we reach a ':', '/' or '>'. The tags name ends there.
            // ':' indicates a argument
            // '/' indicates a directive tag
            // '>' indicates a closing tag
            int tagNameEndIndex = tagNameStartIndex;
            boolean hasArguments = false;
            while (tagNameEndIndex < tagCloseBracketIndex) {
                char nameEndToken = inputText.charAt(tagNameEndIndex);
                if(nameEndToken == TAG_SEPARATOR) {
                    hasArguments = true;
                    break;
                }
                if (nameEndToken == TAG_SLASH) {
                    break;
                }
                tagNameEndIndex++;
            }

            // Create the start and end position of the argument and
            // let the TagHandler decode the string according to their needs.
            final int argumentStart;
            final int argumentEnd;
            if(hasArguments) {
                argumentStart = tagNameEndIndex + 1;
                argumentEnd = tagCloseBracketIndex;
            } else {
                argumentStart = -1;
                argumentEnd = -1;
            }

            // Execute the tag handlers
            boolean wasHandled = false;
            for (TagHandler tagHandler : tagHandlers) {
                if (!tagHandler.canHandle(inputText, tagNameStartIndex, tagNameEndIndex)) {
                    continue;
                }

                // Flush everything before the current tag
                // Take in example this string:
                // "First<color:#ffffff>Second"
                // The part "First" gets flushed with the current style.
                // The tag now gets parsed and applied onto the style.
                // After that the Second will be flushed by
                //  - the next handler
                //  - no handler can handle it
                //  - it's the end of the parsed text
                flushTextContent(state, inputText, textStartIndex, tagOpenBracketIndex);

                wasHandled = tagHandler.handle(
                    state,
                    inputText,
                    tagNameStartIndex, tagNameEndIndex,
                    argumentStart, argumentEnd,
                    action
                );

                //wasHandled = true;
                break;
            }

            // Check if the text was handled
            // Unhandled text gets append as a raw message with the current styling.
            // otherwise, we don't touch it.
            if (!wasHandled) {
                flushTextContent(state, inputText, tagOpenBracketIndex, textEndIndex);
            }

            // Move the cursor to the next character after the closing bracket
            cursor = tagCloseBracketIndex + 1;
            // Set the content start, so the flushing below flushes all leftover text as well.
            textStartIndex = cursor;
        }

        // Flush any remaining inputText onto the currently pending message.
        flushTextContent(state, inputText, textStartIndex, inputLength);

        return state.getRootMessage();
    }

    private static int findTagEnd(String string, int startIndex) {
        if (string == null || string.isEmpty()) {
            return -1;
        }
        final int length = string.length();
        for (int i = startIndex; i < length; i++) {
            if (string.charAt(i) == TAG_CLOSE) {
                return i;
            }
        }
        return -1;
    }

    public void flushTextContent(MessageBuilder state, String text, int contentStart, int contentEnd) {
        if(contentStart >= contentEnd) {
            return;
        }
        String textPart = text.substring(contentStart, contentEnd);
        state.appendStyledText(textPart);
    }
}
