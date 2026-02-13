package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagType;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;

public final class BoldTagHandler extends TagHandler {

    public static final BoldTagHandler INSTANCE = new BoldTagHandler();

    private static final String[] TAGS = new String[]{"bold", "b"};

    @Override
    public boolean canHandle(@Nonnull MessageBuilder state, int nameStart, int nameEnd, @Nonnull TagType action) {
        if(!state.isType(TagType.Open) && !state.isType(TagType.Close)) {
            return false;
        }
        return hasTagOf(TAGS, state.getInputText(), nameStart, nameEnd);
    }

    @Override
    public boolean handle(@Nonnull MessageBuilder state,
                          int nameStart, int nameEnd,
                          int argumentStart, int argumentEnd,
                          @Nonnull TagType action) {
        switch (action) {
            case Open -> {
                state.bold = true;
                return true;
            }
            case Close -> {
                state.bold = false;
                return true;
            }
        }
        return false;
    }
}
