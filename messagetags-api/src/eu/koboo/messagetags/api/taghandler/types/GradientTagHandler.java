package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;
import java.util.List;
import javax.annotation.Nonnull;
import org.bouncycastle.util.Arrays;

public final class GradientTagHandler extends TagHandler {

    public static final GradientTagHandler INSTANCE = new GradientTagHandler();

    private static final String[] TAGS = new String[]{"gradient", "grnt"};

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
                int[] colors = createColorList(state, argument);
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

    private int[] createColorList(MessageBuilder state, String argument) {
        if (argument == null) {
            return null;
        }
        String[] colorSplit = argument.split(":");
        if (colorSplit.length < 2) {
            return null;
        }
        int[] colors = new int[0];
        for (String colorString : colorSplit) {
            int colorValue = state.parseColor(colorString);
            if (colorValue == -1) {
                return null;
            }
            colors = Arrays.append(colors, colorValue);
        }
        return colors;
    }
}
