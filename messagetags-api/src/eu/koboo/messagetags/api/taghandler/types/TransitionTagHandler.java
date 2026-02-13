package eu.koboo.messagetags.api.taghandler.types;

import com.hypixel.hytale.common.util.ArrayUtil;
import eu.koboo.messagetags.api.color.ColorUtils;
import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagHandler;
import eu.koboo.messagetags.api.taghandler.TagType;

import javax.annotation.Nonnull;

public final class TransitionTagHandler extends TagHandler {

    public static final TransitionTagHandler INSTANCE = new TransitionTagHandler();

    private static final String[] TAGS = new String[]{"transition", "trnsn"};

    @Override
    public boolean canHandle(@Nonnull ParseContext context) {
        if (!context.isType(TagType.Open) && !context.isType(TagType.Close)) {
            return false;
        }
        return context.hasTagOf(TAGS);
    }

    @Override
    public boolean handle(@Nonnull ParseContext context) {
        switch (context.getCurrentType()) {
            case Open -> {
                String argument = context.getArgument();
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
                String[] colors = createColorList(context, colorSplit, length, lastCursor);
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
                context.color = color;
                return true;
            }
            case Close -> {
                context.color = null;
                return true;
            }
        }
        return false;
    }

    private String[] createColorList(ParseContext state, String[] colorSplit, int length, int lastCursor) {
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
