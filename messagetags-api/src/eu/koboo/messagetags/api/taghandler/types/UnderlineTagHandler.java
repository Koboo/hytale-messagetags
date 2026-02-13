package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagHandler;
import eu.koboo.messagetags.api.taghandler.TagType;

import javax.annotation.Nonnull;

public final class UnderlineTagHandler extends TagHandler {

    public static final UnderlineTagHandler INSTANCE = new UnderlineTagHandler();

    private static final String[] TAGS = new String[]{"underline", "ul", "underlined"};

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
                context.underline = true;
                return true;
            }
            case Close -> {
                context.underline = false;
                return true;
            }
        }
        return false;
    }
}
