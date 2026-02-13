package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagType;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;

public final class ResetTagHandler extends TagHandler {

    public static final ResetTagHandler INSTANCE = new ResetTagHandler();

    private static final String[] TAGS = new String[]{"reset", "r"};

    @Override
    public boolean canHandle(@Nonnull MessageBuilder state, int nameStart, int nameEnd, @Nonnull TagType currentType) {
        if(!state.isType(TagType.Open) && !state.isType(TagType.Directive)) {
            return false;
        }
        return hasTagOf(TAGS, state.getInputText(), nameStart, nameEnd);
    }

    @Override
    public boolean handle(@Nonnull MessageBuilder state,
                          int nameStart, int nameEnd,
                          int argumentStart, int argumentEnd,
                          @Nonnull TagType currentType) {
        if (currentType != TagType.Open && currentType != TagType.Directive) {
            return false;
        }
        state.resetStyle();
        return true;
    }
}
