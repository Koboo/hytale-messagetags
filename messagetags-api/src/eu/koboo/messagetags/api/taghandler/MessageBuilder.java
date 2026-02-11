package eu.koboo.messagetags.api.taghandler;

import com.hypixel.hytale.protocol.FormattedMessage;
import com.hypixel.hytale.protocol.MaybeBool;
import com.hypixel.hytale.server.core.Message;
import eu.koboo.messagetags.api.MessageParser;
import eu.koboo.messagetags.api.colors.ColorUtils;

import eu.koboo.messagetags.api.colors.NamedColor;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

public final class MessageBuilder {

    private MessageParser parser;
    private String inputText;
    private boolean strip;
    private List<FormattedMessage> messageList = new ArrayList<>();

    public boolean bold;
    public boolean italic;
    public boolean monospaced;
    public boolean underlined;
    public int color;
    public String link;
    public int[] gradientColors;

    public MessageBuilder() {
    }

    public void init(MessageParser parser, String inputText, boolean strip) {
        this.parser = parser;
        this.inputText = inputText;
        this.strip = strip;
        this.messageList.clear();
        resetStyle();
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
        monospaced = false;
        underlined = false;
        color = -1;
        link = null;
        gradientColors = null;
    }

    public void appendStyledText(String textPart) {
        if(textPart == null || textPart.isEmpty()) {
            throw new NullPointerException("textPart cannot be null or empty");
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
        if (monospaced) {
            message.monospace = MaybeBool.True;
        }
        if (underlined) {
            message.underlined = MaybeBool.True;
        }
        if (color != -1) {
            message.color = ColorUtils.rgbToHex(color);
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

            int hexColor = ColorUtils.interpolateColor(gradientColors, progress);
            if (hexColor != -1) {
                message.color = ColorUtils.rgbToHex(hexColor);
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

    public int parseColor(String colorString) {
        if (colorString == null) {
            return -1;
        }
        if(colorString.indexOf(' ') != -1) {
            colorString = colorString.replace(' ', Character.MIN_VALUE);
        }
        int length = colorString.length();
        if(length == 0) {
            return -1;
        }
        char firstCharacter = colorString.charAt(0);

        // #ffffff
        if (firstCharacter == MessageParser.COLOR_PREFIX && length == 7) {
            return ColorUtils.hexToRGB(colorString);
        }

        // white -> #ffffff
        NamedColor namedColor = parser.getNamedColorByName(colorString);
        if (namedColor != null) {
            return namedColor.value;
        }

        // &f -> #ffffff
        // §f -> #ffffff
        boolean startsWithColorToken = firstCharacter == MessageParser.COLOR_AMPERSAND
            || firstCharacter == MessageParser.COLOR_SECTION;
        if (length == 2 && startsWithColorToken) {
            char colorChar = colorString.substring(1).charAt(0);
            namedColor = parser.getNamedColorByChar(colorChar);
            if (namedColor != null) {
                return namedColor.value;
            }
        }

        // 255,255,255 -> #ffffff
        if (colorString.indexOf(',') != -1) {
            if (colorString.indexOf(' ') != -1) {
                colorString = colorString.replace(' ', Character.MIN_VALUE);
            }
            String[] splitRGB = colorString.split(",");
            if (splitRGB.length != 3) {
                return -1;
            }
            short r = ColorUtils.parseShort(splitRGB[0]);
            short g = ColorUtils.parseShort(splitRGB[1]);
            short b = ColorUtils.parseShort(splitRGB[2]);
            return ColorUtils.rgbToInt(r, g, b);
        }
        return -1;
    }

    @Nullable
    public String getArgument(int argumentStart, int argumentEnd) {
        if (argumentStart == -1 || argumentEnd == -1) {
            return null;
        }
        if (argumentStart >= argumentEnd) {
            return null;
        }
        String argument = inputText.substring(argumentStart, argumentEnd).trim();
        if (argument.isEmpty()) {
            return null;
        }
        return argument;
    }
}
