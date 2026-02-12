package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;

public final class ResetTagHandler extends TagHandler {

    public static final ResetTagHandler INSTANCE = new ResetTagHandler();

    private static final String[] TAGS = new String[]{"reset", "r"};

    @Override
    public boolean canHandle(@Nonnull MessageBuilder state, int nameStart, int nameEnd) {
        return hasTagOf(TAGS, state.getInputText(), nameStart, nameEnd);
    }

    @Override
    public boolean handle(@Nonnull MessageBuilder state,
                          int nameStart, int nameEnd,
                          int argumentStart, int argumentEnd,
                          @Nonnull TagAction action) {
        if (action != TagAction.Open && action != TagAction.Directive) {
            return false;
        }
        state.resetStyle();
        return true;
    }
}
