package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;
import java.util.List;

public final class UnderlinedTagHandler extends TagHandler {

    public static final UnderlinedTagHandler INSTANCE = new UnderlinedTagHandler();

    private static final String[] TAGS = new String[]{"underline", "ul"};

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
                state.underlined = true;
                return true;
            }
            case Close -> {
                state.underlined = false;
                return true;
            }
        }
        return false;
    }
}
