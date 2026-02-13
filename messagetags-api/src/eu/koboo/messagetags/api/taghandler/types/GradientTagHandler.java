package eu.koboo.messagetags.api.taghandler.types;

import com.hypixel.hytale.common.util.ArrayUtil;
import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagHandler;
import eu.koboo.messagetags.api.taghandler.TagType;

import javax.annotation.Nonnull;

public final class GradientTagHandler extends TagHandler {

    public static final GradientTagHandler INSTANCE = new GradientTagHandler();

    private static final String[] TAGS = new String[]{"gradient", "grnt"};

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
