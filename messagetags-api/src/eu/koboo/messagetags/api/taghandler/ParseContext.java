package eu.koboo.messagetags.api.taghandler;

import com.hypixel.hytale.protocol.FormattedMessage;
import com.hypixel.hytale.protocol.MaybeBool;
import com.hypixel.hytale.server.core.Message;
import eu.koboo.messagetags.api.MessageParser;
import eu.koboo.messagetags.api.color.ColorUtils;
import eu.koboo.messagetags.api.color.NamedColor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public final class ParseContext {

    private final MessageParser parser;
    private final String inputText;
    public final boolean strip;
    private final List<FormattedMessage> messageList = new ArrayList<>();

    public boolean bold;
    public boolean italic;
    public boolean monospace;
    public boolean underline;
    public String color;
    public String link;
    public String[] gradientColors;

    private int currentNameStart;
    private int currentNameEnd;
    private int currentArgumentStart;
    private int currentArgumentEnd;
    private TagType currentType;

    public ParseContext(MessageParser parser, String inputText, boolean strip) {
        this.parser = parser;
        this.inputText = inputText;
        this.strip = strip;
    }

    public String getInputText() {
        return inputText;
    }

    public Message buildRootMessage() {
        FormattedMessage rootMessage = new FormattedMessage();
        int arraySize = messageList.size();
        FormattedMessage[] messageArray = new FormattedMessage[arraySize];
        for (int i = 0; i < arraySize; i++) {
            messageArray[i] = messageList.get(i);
        }
        rootMessage.children = messageArray;
        messageList.clear();
        return new Message(rootMessage);
    }

    public void appendMessage(FormattedMessage message) {
        messageList.add(message);
    }

    public void resetStyle() {
        bold = false;
        italic = false;
        monospace = false;
        underline = false;
        color = null;
        link = null;
        gradientColors = null;
    }

    public void appendStyledText(String textPart) {
        if (textPart == null || textPart.isEmpty()) {
            return;
        }
        if (gradientColors != null && gradientColors.length != 0) {
            List<FormattedMessage> gradientMessageList = createGradientMessageList(textPart);
            for (FormattedMessage gradientMessage : gradientMessageList) {
                appendMessage(gradientMessage);
            }
            return;
        }
        FormattedMessage message = createByText(textPart);
        appendStyledMessage(message);
    }

    public void appendStyledMessage(FormattedMessage message) {
        applyStyleTo(message);
        appendMessage(message);
    }

    private void applyStyleTo(FormattedMessage message) {
        if (strip) {
            return;
        }

        if (bold) {
            message.bold = MaybeBool.True;
        }
        if (italic) {
            message.italic = MaybeBool.True;
        }
        if (monospace) {
            message.monospace = MaybeBool.True;
        }
        if (underline) {
            message.underlined = MaybeBool.True;
        }
        if (color != null) {
            message.color = color;
        }
        if (link != null && !link.isEmpty()) {
            message.link = link;
        }
    }

    private List<FormattedMessage> createGradientMessageList(String text) {
        if (strip) {
            return List.of(createByText(text));
        }
        List<FormattedMessage> gradientMessageList = new ArrayList<>();
        int textLength = text.length();
        for (int cursor = 0; cursor < textLength; cursor++) {
            char textChar = text.charAt(cursor);
            float progress = (float) cursor / (textLength - 1);

            FormattedMessage message = createByText(String.valueOf(textChar));
            applyStyleTo(message);

            String hexColor = ColorUtils.interpolateColor(gradientColors, progress);
            if (hexColor != null) {
                message.color = hexColor;
            }

            gradientMessageList.add(message);
        }
        return gradientMessageList;
    }

    public FormattedMessage createByText(String rawText) {
        FormattedMessage message = new FormattedMessage();
        message.rawText = rawText;
        return message;
    }

    public FormattedMessage createByTranslation(String translation) {
        FormattedMessage message = new FormattedMessage();
        message.messageId = translation;
        return message;
    }

    public String parseColor(String colorString) {
        if (colorString == null) {
            return null;
        }
        int length = colorString.length();
        if (length < 1) {
            return null;
        }
        char firstCharacter = colorString.charAt(0);

        // #ffffff
        if (firstCharacter == MessageParser.COLOR_PREFIX && length == 7) {
            return colorString;
        }

        // white -> #ffffff
        NamedColor namedColor = parser.getNamedColorByName(colorString);
        if (namedColor != null) {
            return namedColor.hexCode();
        }
        return null;
    }

    @Nullable
    public String getArgument() {
        if (currentArgumentStart == -1 || currentArgumentEnd == -1) {
            return null;
        }
        if (currentArgumentStart >= currentArgumentEnd) {
            return null;
        }
        String argument = inputText.substring(currentArgumentStart, currentArgumentEnd).trim();
        if (argument.isEmpty()) {
            return null;
        }
        return argument;
    }

    public boolean isType(@Nonnull TagType requiredType) {
        return currentType == requiredType;
    }

    @Nonnull
    public String getCurrentTag() {
        return inputText.substring(currentNameStart, currentNameEnd);
    }

    private boolean equalsIgnoreCase(@Nonnull String string) {

        // Start is bigger than or equal to the end? Can't be correct.
        if (currentNameStart >= currentNameEnd) {
            return false;
        }

        // Check if the given root and the text have equal length
        int checkedLength = currentNameEnd - currentNameStart;
        if (checkedLength != string.length()) {
            return false;
        }

        for (int rootIndex = 0; rootIndex < checkedLength; rootIndex++) {
            char rootChar = toLowerAscii(inputText.charAt(currentNameStart + rootIndex));
            char tagChar = toLowerAscii(string.charAt(rootIndex));
            if (rootChar != tagChar) {
                return false;
            }
        }
        return true;
    }

    public boolean hasTagOf(@Nonnull String[] tagList) {
        int length = tagList.length;
        for (int i = 0; i < length; i++) {
            String tag = tagList[i];
            if (equalsIgnoreCase(tag)) {
                return true;
            }
        }
        return false;
    }

    private static char toLowerAscii(char c) {
        return (c >= 'A' && c <= 'Z') ? (char) (c | 0x20) : c;
    }

    @Nonnull
    public TagType getCurrentType() {
        return currentType;
    }

    public void updateCurrentTag(
        int nameStartPos, int nameEndPos,
        int argumentStart, int argumentEnd,
        @Nonnull TagType action) {
        this.currentNameStart = nameStartPos;
        this.currentNameEnd = nameEndPos;
        this.currentArgumentStart = argumentStart;
        this.currentArgumentEnd = argumentEnd;
        this.currentType = action;
    }
}

