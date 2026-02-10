package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.colors.ColorParser;
import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class GradientTagHandler extends TagHandler {

    public static final GradientTagHandler INSTANCE = new GradientTagHandler();

    private static final List<String> TAGS = List.of("gradient", "grnt", "grad");

    @Override
    public boolean canHandle(@Nonnull String root, int nameStart, int nameEnd) {
        return hasTagOf(TAGS, root, nameStart, nameEnd);
    }

    @Override
    public boolean handle(@Nonnull MessageBuilder state,
                          @Nonnull String root,
                          int nameStart, int nameEnd,
                          int argumentStart, int argumentEnd,
                          @Nonnull TagAction action) {
        switch (action) {
            case Open -> {
                String argument = getArgument(root, argumentStart, argumentEnd);
                List<String> colorList = createColorList(argument);
                if (colorList == null || colorList.isEmpty()) {
                    return false;
                }
                state.gradientColors = colorList;
                return true;
            }
            case Close -> {
                if (state.gradientColors != null) {
                    state.gradientColors.clear();
                    state.gradientColors = null;
                }
                return true;
            }
        }
        return false;
    }

    private List<String> createColorList(String argument) {
        if (argument == null) {
            return null;
        }
        String[] colorSplit = argument.split(":");
        if (colorSplit.length < 2) {
            return null;
        }
        List<String> colorList = new ArrayList<>();
        for (String colorString : colorSplit) {
            String color = ColorParser.parseColor(colorString);
            if (color == null) {
                return null;
            }
            colorList.add(color);
        }
        return colorList;
    }
}
