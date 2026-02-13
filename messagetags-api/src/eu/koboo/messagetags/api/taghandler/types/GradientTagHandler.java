package eu.koboo.messagetags.api.taghandler.types;

import com.hypixel.hytale.common.util.ArrayUtil;
import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;

public final class GradientTagHandler extends TagHandler {

    public static final GradientTagHandler INSTANCE = new GradientTagHandler();

    private static final String[] TAGS = new String[]{"gradient", "grnt"};

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
                String[] colors = createColorList(state, argument);
                if (colors == null || colors.length == 0) {
                    return false;
                }
                state.gradientColors = colors;
                return true;
            }
            case Close -> {
                state.gradientColors = null;
                return true;
            }
        }
        return false;
    }

    private String[] createColorList(MessageBuilder state, String argument) {
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
