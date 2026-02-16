package eu.koboo.messagetags.api.taghandler.baked;

import eu.koboo.messagetags.api.taghandler.BakedTagHandler;
import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagType;

import javax.annotation.Nonnull;
import java.util.Set;

public final class ItalicTagHandler extends BakedTagHandler {

    private static final String[] TAGS = new String[]{"italic", "i", "em"};
    private static final Set<TagType> TYPES = Set.of(TagType.Open, TagType.Close);

    public static final ItalicTagHandler INSTANCE = new ItalicTagHandler();

    public ItalicTagHandler() {
        super(TAGS, TYPES);
    }

    @Override
    public boolean handle(@Nonnull ParseContext context) {
        switch (context.getCurrentType()) {
            case Open -> {
                context.italic = true;
                return true;
            }
            case Close -> {
                context.italic = false;
                return true;
            }
        }
        return false;
    }
}
