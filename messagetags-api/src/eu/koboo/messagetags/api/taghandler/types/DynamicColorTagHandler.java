package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagType;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;

public final class DynamicColorTagHandler extends TagHandler {

    public static final DynamicColorTagHandler INSTANCE = new DynamicColorTagHandler();

    @Override
    public boolean canHandle(@Nonnull ParseContext context) {
        return context.isType(TagType.Open);
    }

    @Override
    public boolean handle(@Nonnull ParseContext context) {
        String tag = context.getCurrentTag();
        String color = context.parseColor(tag);
        if (color == null) {
            return false;
        }
        context.color = color;
        return true;
    }
}
