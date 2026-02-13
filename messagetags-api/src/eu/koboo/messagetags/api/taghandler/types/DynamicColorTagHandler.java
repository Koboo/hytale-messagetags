package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;

public final class DynamicColorTagHandler extends TagHandler {

    public static final DynamicColorTagHandler INSTANCE = new DynamicColorTagHandler();

    @Override
    public boolean canHandle(@Nonnull MessageBuilder state, int nameStart, int nameEnd, @Nonnull TagAction action) {
        if(!state.isType(TagAction.Open)) {
            return false;
        }
        String tag = state.getCurrentTag();
        String color = state.parseColor(tag);
        return color != null;
    }

    @Override
    public boolean handle(@Nonnull MessageBuilder state,
                          int nameStart, int nameEnd,
                          int argumentStart, int argumentEnd,
                          @Nonnull TagAction action) {
        String tag = state.getCurrentTag();
        String color = state.parseColor(tag);
        if (color == null) {
            return false;
        }
        state.color = color;
        return true;
    }
}
