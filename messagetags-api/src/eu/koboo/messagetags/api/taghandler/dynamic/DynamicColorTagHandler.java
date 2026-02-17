package eu.koboo.messagetags.api.taghandler.dynamic;

import eu.koboo.messagetags.api.taghandler.DynamicTagHandler;
import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagType;

import javax.annotation.Nonnull;

public final class DynamicColorTagHandler extends DynamicTagHandler {

    public static final DynamicColorTagHandler INSTANCE = new DynamicColorTagHandler();

    @Override
    public boolean canHandle(@Nonnull ParseContext context) {
        if (context.hasArguments()) {
            return false;
        }
        return context.isType(TagType.Open) || context.isType(TagType.Directive);
    }

    @Override
    public boolean handle(@Nonnull ParseContext context) {
        String tag = context.getCurrentTag();
        String color = context.parser.parseColor(tag);
        if (color == null) {
            return false;
        }
        context.color = color;
        return true;
    }
}
