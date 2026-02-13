package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagHandler;
import eu.koboo.messagetags.api.taghandler.TagType;

import javax.annotation.Nonnull;

public final class MonospaceTagHandler extends TagHandler {

    public static final MonospaceTagHandler INSTANCE = new MonospaceTagHandler();

    private static final String[] TAGS = new String[]{"monospace", "mono", "monospaced"};

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
                context.monospace = true;
                return true;
            }
            case Close -> {
                context.monospace = false;
                return true;
            }
        }
        return false;
    }
}
