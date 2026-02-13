package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagType;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;

public final class ResetTagHandler extends TagHandler {

    public static final ResetTagHandler INSTANCE = new ResetTagHandler();

    private static final String[] TAGS = new String[]{"reset", "r"};

    @Override
    public boolean canHandle(@Nonnull ParseContext context) {
        if(!context.isType(TagType.Open) && !context.isType(TagType.Directive)) {
            return false;
        }
        return context.hasTagOf(TAGS);
    }

    @Override
    public boolean handle(@Nonnull ParseContext context) {
        context.resetStyle();
        return true;
    }
}
