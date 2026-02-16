package eu.koboo.messagetags.api.taghandler.baked;

import eu.koboo.messagetags.api.taghandler.BakedTagHandler;
import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagType;

import javax.annotation.Nonnull;
import java.util.Set;

public final class ResetTagHandler extends BakedTagHandler {

    private static final String[] TAGS = new String[]{"reset", "r"};
    private static final Set<TagType> TYPES = Set.of(TagType.Open, TagType.Directive);

    public static final ResetTagHandler INSTANCE = new ResetTagHandler();

    public ResetTagHandler() {
        super(TAGS, TYPES);
    }

    @Override
    public boolean handle(@Nonnull ParseContext context) {
        context.resetStyle();
        return true;
    }
}
