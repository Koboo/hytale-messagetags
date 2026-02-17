package eu.koboo.messagetags.api.taghandler;

import com.hypixel.hytale.protocol.FormattedMessage;
import com.hypixel.hytale.protocol.MaybeBool;
import com.hypixel.hytale.server.core.Message;
import eu.koboo.messagetags.api.MessageParser;
import eu.koboo.messagetags.api.color.ColorUtils;
import eu.koboo.messagetags.api.color.NamedColor;
import eu.koboo.messagetags.api.variable.TagPlaceholder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ParseContext {

    public final MessageParser parser;
    private final String inputText;
    private final List<FormattedMessage> messageList = new ArrayList<>();

    public final boolean strip;
    public final Map<String, TagPlaceholder> placeholderMap;

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

    public ParseContext(MessageParser parser, String inputText, Map<String, TagPlaceholder> placeholderMap, boolean strip) {
        this.parser = parser;
        this.inputText = inputText;
        this.placeholderMap = placeholderMap;
        this.strip = strip;
    }

    @Nonnull
    public Message buildRootMessage() {
        FormattedMessage rootMessage = new FormattedMessage();
        int arraySize = messageList.size();
        FormattedMessage[] messageArray = new FormattedMessage[arraySize];
        for (int i = 0; i < arraySize; i++) {
            messageArray[i] = messageList.get(i);
        }
        rootMessage.children = messageArray;
        messageList.clear();
        if (placeholderMap != null) {
            placeholderMap.clear();
        }
        return new Message(rootMessage);
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

    public void appendStyledText(@Nonnull String textPart) {
        if (textPart.isEmpty()) {
            return;
        }
        if (gradientColors != null && gradientColors.length != 0) {
            List<FormattedMessage> gradientMessageList = createGradientMessageList(textPart);
            messageList.addAll(gradientMessageList);
            return;
        }

        FormattedMessage message = new FormattedMessage();
        message.rawText = textPart;
        appendMessage(message);
    }

    public void appendMessage(@Nonnull FormattedMessage message) {
        appendMessage(message, true);
    }

    public void appendMessage(@Nonnull FormattedMessage message, boolean styled) {
        if (styled) {
            applyStyleTo(message);
        }
        messageList.add(message);
    }

    private void applyStyleTo(@Nonnull FormattedMessage message) {
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

    @Nonnull
    private List<FormattedMessage> createGradientMessageList(@Nonnull String text) {
        if (strip) {
            FormattedMessage message = new FormattedMessage();
            message.rawText = text;
            return List.of(message);
        }
        List<FormattedMessage> gradientMessageList = new ArrayList<>();
        int textLength = text.length();
        for (int cursor = 0; cursor < textLength; cursor++) {
            char textChar = text.charAt(cursor);
            float progress = (float) cursor / (textLength - 1);

            FormattedMessage message = new FormattedMessage();
            message.rawText = String.valueOf(textChar);
            applyStyleTo(message);

            String hexColor = ColorUtils.interpolateColor(gradientColors, progress);
            if (hexColor != null) {
                message.color = hexColor;
            }

            gradientMessageList.add(message);
        }
        return gradientMessageList;
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

    @Nonnull
    public TagType getCurrentType() {
        return currentType;
    }

    public boolean hasArguments() {
        return currentArgumentStart != -1 && currentArgumentEnd != -1;
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

