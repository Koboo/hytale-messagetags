package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.colors.ColorParser;
import eu.koboo.messagetags.api.colors.ColorUtils;
import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class TransitionTagHandler extends TagHandler {

    public static final TransitionTagHandler INSTANCE = new TransitionTagHandler();

    private static final List<String> TAGS = List.of("transition", "trnsn");

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
                if(argument == null) {
                    return false;
                }
                String[] colorSplit = argument.split(":");
                int length = colorSplit.length;
                // At least 2 color and a phase
                if(length < 3) {
                    return false;
                }
                int lastCursor = length - 1;
                List<String> colorList = createColorList(colorSplit, length, lastCursor);
                if(colorList == null || colorList.isEmpty()) {
                    return false;
                }
                float phase = getPhase(colorSplit, lastCursor);
                if(phase < 0 || phase > 1) {
                    return false;
                }
                String phaseColor = ColorUtils.interpolateColor(colorList, phase);
                if(phaseColor == null) {
                    return false;
                }
                state.color = phaseColor;
                return true;
            }
            case Close -> {
                state.color = null;
                return true;
            }
        }
        return false;
    }

    private List<String> createColorList(String[] colorSplit, int length, int lastCursor) {
        List<String> colorList = new ArrayList<>();
        for (int cursor = 0; cursor < length; cursor++) {
            if(cursor == lastCursor) {
                break;
            }
            String colorString = colorSplit[cursor];
            String color = ColorParser.parseColor(colorString);
            if(color == null) {
                return null;
            }
            colorList.add(color);
        }
        return colorList;
    }

    private float getPhase(String[] colorSplit, int lastCursor) {
        try {
            return Float.parseFloat(colorSplit[lastCursor]);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
