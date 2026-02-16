package eu.koboo.messagetags.api.taghandler.baked;

import eu.koboo.messagetags.api.taghandler.BakedTagHandler;
import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagType;

import javax.annotation.Nonnull;
import java.util.Set;

public final class LinkTagHandler extends BakedTagHandler {

    private static final String[] TAGS = new String[]{"link", "url"};
    private static final Set<TagType> TYPES = Set.of(TagType.Open, TagType.Close);

    public static final LinkTagHandler INSTANCE = new LinkTagHandler();

    public LinkTagHandler() {
        super(TAGS, TYPES);
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
