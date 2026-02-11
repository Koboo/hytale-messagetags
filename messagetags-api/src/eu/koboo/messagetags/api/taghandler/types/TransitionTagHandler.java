package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.colors.ColorUtils;
import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import org.bouncycastle.util.Arrays;

public final class TransitionTagHandler extends TagHandler {

    public static final TransitionTagHandler INSTANCE = new TransitionTagHandler();

    private static final List<String> TAGS = List.of("transition", "trnsn");

    @Override
    public boolean canHandle(@Nonnull MessageBuilder state, int nameStart, int nameEnd) {
        return hasTagOf(TAGS, state.getInputText(), nameStart, nameEnd);
    }

    @Override
    public boolean handle(@Nonnull MessageBuilder state,
                          int nameStart, int nameEnd,
                          int argumentStart, int argumentEnd,
                          @Nonnull TagAction action) {
        switch (action) {
            case Open -> {
                String argument = state.getArgument(argumentStart, argumentEnd);
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
                int[] colors = createColorList(state, colorSplit, length, lastCursor);
                if (colors == null || colors.length == 0) {
                    return false;
                }
                float phase = getPhase(colorSplit, lastCursor);
                if (phase < 0 || phase > 1) {
                    return false;
                }
                int phaseColor = ColorUtils.interpolateColor(colors, phase);
                if (phaseColor == -1) {
                    return false;
                }
                state.color = phaseColor;
                return true;
            }
            case Close -> {
                state.color = -1;
                return true;
            }
        }
        return false;
    }

    private int[] createColorList(MessageBuilder state, String[] colorSplit, int length, int lastCursor) {
        int[] colors = new int[0];
        for (int cursor = 0; cursor < length; cursor++) {
            if (cursor == lastCursor) {
                break;
            }
            String colorString = colorSplit[cursor];
            int color = state.parseColor(colorString);
            if (color == -1) {
                return null;
            }
            colors = Arrays.append(colors, color);
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
