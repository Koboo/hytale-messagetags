package eu.koboo.messagetags.api.taghandler.baked;

import com.hypixel.hytale.common.util.ArrayUtil;
import eu.koboo.messagetags.api.color.ColorUtils;
import eu.koboo.messagetags.api.taghandler.BakedTagHandler;
import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagType;

import javax.annotation.Nonnull;
import java.util.Set;

public final class TransitionTagHandler extends BakedTagHandler {

    private static final String[] TAGS = new String[]{"transition", "trnsn"};
    private static final Set<TagType> TYPES = Set.of(TagType.Open, TagType.Close);

    public static final TransitionTagHandler INSTANCE = new TransitionTagHandler();

    public TransitionTagHandler() {
        super(TAGS, TYPES);
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
