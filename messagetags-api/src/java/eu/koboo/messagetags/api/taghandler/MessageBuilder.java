package eu.koboo.messagetags.api.taghandler;

import com.hypixel.hytale.protocol.MaybeBool;
import com.hypixel.hytale.server.core.Message;
import eu.koboo.messagetags.api.colors.ColorUtils;

import java.util.ArrayList;
import java.util.List;

public final class MessageBuilder {

    private final boolean strip;
    private final Message rootMessage = Message.empty();

    public Boolean bold;
    public Boolean italic;
    public Boolean monospaced;
    public Boolean underlined;
    public String color;
    public String link;
    public List<String> gradientColors;

    public MessageBuilder(boolean strip) {
        this.strip = strip;
    }

    public Message getRootMessage() {
        return rootMessage;
    }

    public void resetStyle() {
        bold = null;
        italic = null;
        monospaced = null;
        color = null;
        link = null;
        if (gradientColors != null) {
            gradientColors.clear();
            gradientColors = null;
        }
    }

    public void appendStyledText(String textPart) {
        if (gradientColors != null && !gradientColors.isEmpty()) {
            List<Message> gradientMessageList = createGradientMessageList(textPart);
            for (Message gradientMessage : gradientMessageList) {
                rootMessage.insert(gradientMessage);
            }
            return;
        }
        Message message = Message.raw(textPart);
        applyStyleTo(message);
        rootMessage.insert(message);
    }

    public void appendStyledMessage(Message message) {
        applyStyleTo(message);
        getRootMessage().insert(message);
    }

    private void applyStyleTo(Message message) {
        if (strip) {
            return;
        }

        if (bold != null && bold) {
            message.bold(true);
        }
        if (italic != null && italic) {
            message.italic(true);
        }
        if (monospaced != null && monospaced) {
            message.monospace(true);
        }
        if (color != null && !color.isEmpty()) {
            message.color(color);
        }
        if (link != null && !link.isEmpty()) {
            message.link(link);
        }
        if (underlined != null && underlined) {
            message.getFormattedMessage().underlined = MaybeBool.True;
        }
    }

    private List<Message> createGradientMessageList(String text) {
        if (strip) {
            return List.of(Message.raw(text));
        }
        List<Message> gradientMessageList = new ArrayList<>();
        int textLength = text.length();
        for (int cursor = 0; cursor < textLength; cursor++) {
            char textChar = text.charAt(cursor);
            float progress = (float) cursor / (textLength - 1);

            Message message = Message.raw(String.valueOf(textChar));
            applyStyleTo(message);

            String hexColor = ColorUtils.interpolateColor(gradientColors, progress);
            if (hexColor != null) {
                message = message.color(hexColor);
            }

            gradientMessageList.add(message);
        }
        return gradientMessageList;
    }
}
