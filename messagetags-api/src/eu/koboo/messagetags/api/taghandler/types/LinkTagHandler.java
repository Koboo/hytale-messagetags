package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagHandler;
import eu.koboo.messagetags.api.taghandler.TagType;

import javax.annotation.Nonnull;

public final class LinkTagHandler extends TagHandler {

    public static final LinkTagHandler INSTANCE = new LinkTagHandler();

    private static final String[] TAGS = new String[]{"link", "url"};

    @Override
    public boolean canHandle(@Nonnull ParseContext context) {
        if (!context.isType(TagType.Open) && !context.isType(TagType.Close)) {
            return false;
        }
        return context.hasTagOf(TAGS);
    }

    @Override
    public boolean handle(@Nonnull ParseContext context) {
        switch (context.getCurrentType()) {
            case Open -> {
                String link = context.getArgument();
                if (link == null) {
                    return false;
                }
                context.link = link;
                return true;
            }
            case Close -> {
                context.link = null;
                return true;
            }
        }
        return false;
    }
}
