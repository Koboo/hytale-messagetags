package eu.koboo.messagetags.api.taghandler;

import javax.annotation.Nonnull;

public abstract class DynamicTagHandler {

    public abstract boolean canHandle(@Nonnull ParseContext context);

    public abstract boolean handle(@Nonnull ParseContext context);
}