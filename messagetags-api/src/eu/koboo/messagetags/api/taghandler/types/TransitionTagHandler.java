package eu.koboo.messagetags.api.taghandler.types;

import com.hypixel.hytale.common.util.ArrayUtil;
import eu.koboo.messagetags.api.colors.ColorUtils;
import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;

public final class TransitionTagHandler extends TagHandler {

    public static final TransitionTagHandler INSTANCE = new TransitionTagHandler();

    private static final String[] TAGS = new String[]{"transition", "trnsn"};

    @Override
    public boolean canHandle(@Nonnull MessageBuilder state, int nameStart, int nameEnd, @Nonnull TagAction action) {
        if(!state.isType(TagAction.Open) && !state.isType(TagAction.Close)) {
            return false;
        }
        return hasTagOf(TAGS, state.getInputText(), nameStart, nameEnd);
    }

    @Override
    public boolean handle(@Nonnull MessageBuilder state,
                          int nameStart, int nameEnd,
                          int argumentStart, int argumentEnd,
                          @Nonnull TagAction action) {
        switch (action) {
            case Open -> {
                String argument = state.getArgument();
                if (argument == null) {
                    return false;
                }
                String[] colorSplit = argument.split(":");
                int length = colorSplit.length;
                // At least 2 color and a phase
                if (length < 3) {
                    return false;
                }
                int lastCursor = length - 1;
                String[] colors = createColorList(state, colorSplit, length, lastCursor);
                if (colors == null || colors.length == 0) {
                    return false;
                }
                float phase = getPhase(colorSplit, lastCursor);
                if (phase < 0 || phase > 1) {
                    return false;
                }
                String color = ColorUtils.interpolateColor(colors, phase);
                if (color == null) {
                    return false;
                }
                state.color = color;
                return true;
            }
            case Close -> {
                state.color = null;
                return true;
            }
        }
        return false;
    }

    private String[] createColorList(MessageBuilder state, String[] colorSplit, int length, int lastCursor) {
        String[] colors = new String[0];
        for (int cursor = 0; cursor < length; cursor++) {
            if (cursor == lastCursor) {
                break;
            }
            String colorString = colorSplit[cursor];
            String color = state.parseColor(colorString);
            if (color == null) {
                return null;
            }
            colors = ArrayUtil.append(colors, color);
        }
        return colors;
    }

    private float getPhase(String[] colorSplit, int lastCursor) {
        try {
            return Float.parseFloat(colorSplit[lastCursor]);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
