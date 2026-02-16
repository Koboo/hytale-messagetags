package eu.koboo.messagetags.api.taghandler.baked;

import com.hypixel.hytale.common.util.ArrayUtil;
import eu.koboo.messagetags.api.taghandler.BakedTagHandler;
import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagType;

import javax.annotation.Nonnull;
import java.util.Set;

public final class GradientTagHandler extends BakedTagHandler {

    private static final String[] TAGS = new String[]{"gradient", "grnt"};
    private static final Set<TagType> TYPES = Set.of(TagType.Open, TagType.Close);

    public static final GradientTagHandler INSTANCE = new GradientTagHandler();

    public GradientTagHandler() {
        super(TAGS, TYPES);
    }

    @Override
    public boolean handle(@Nonnull ParseContext context) {
        switch (context.getCurrentType()) {
            case Open -> {
                String argument = context.getArgument();
                String[] colors = createColorList(context, argument);
                if (colors == null || colors.length == 0) {
                    return false;
                }
                context.gradientColors = colors;
                return true;
            }
            case Close -> {
                context.gradientColors = null;
                return true;
            }
        }
        return false;
    }

    private String[] createColorList(ParseContext state, String argument) {
        if (argument == null) {
            return null;
        }
        String[] colorSplit = argument.split(":");
        if (colorSplit.length < 2) {
            return null;
        }
        String[] colors = new String[0];
        for (String colorString : colorSplit) {
            String color = state.parseColor(colorString);
            if (color == null) {
                return null;
            }
            colors = ArrayUtil.append(colors, color);
        }
        return colors;
    }
}
