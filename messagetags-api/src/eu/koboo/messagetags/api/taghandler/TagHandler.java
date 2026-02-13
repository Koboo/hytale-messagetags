package eu.koboo.messagetags.api.taghandler;

import javax.annotation.Nonnull;

public abstract class TagHandler {

    public abstract boolean canHandle(@Nonnull ParseContext context);

    public abstract boolean handle(@Nonnull ParseContext context);
}