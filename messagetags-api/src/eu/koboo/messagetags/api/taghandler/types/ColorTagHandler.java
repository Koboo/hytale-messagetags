package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;
import java.util.List;

public final class ColorTagHandler extends TagHandler {

    public static final ColorTagHandler INSTANCE = new ColorTagHandler();

    private static final List<String> TAGS = List.of("color", "colour", "c");

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
                int colorValue = state.parseColor(argument);
                if (colorValue == -1) {
                    return false;
                }
                state.color = colorValue;
                return true;
            }
            case Close -> {
                state.color = -1;
                return true;
            }
        }
        return false;
    }
}
