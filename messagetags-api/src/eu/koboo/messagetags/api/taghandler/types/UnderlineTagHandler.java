package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagType;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;

public final class UnderlineTagHandler extends TagHandler {

    public static final UnderlineTagHandler INSTANCE = new UnderlineTagHandler();

    private static final String[] TAGS = new String[]{"underline", "ul"};

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
                state.underline = true;
                return true;
            }
            case Close -> {
                state.underline = false;
                return true;
            }
        }
        return false;
    }
}
