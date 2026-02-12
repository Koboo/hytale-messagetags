package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;

public final class ColorTagHandler extends TagHandler {

    public static final ColorTagHandler INSTANCE = new ColorTagHandler();

    private static final String[] TAGS = new String[]{"color", "colour", "c"};

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
                String color = state.parseColor(argument);
                if (color == null) {
                    return false;
                }
                state.color = color;
                return true;
            }
            case Close -> {
                state.color = null;
                return true;
            }
        }
        return false;
    }
}
