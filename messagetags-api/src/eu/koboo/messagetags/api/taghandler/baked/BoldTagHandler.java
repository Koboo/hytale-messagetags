package eu.koboo.messagetags.api.taghandler.baked;

import eu.koboo.messagetags.api.taghandler.BakedTagHandler;
import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagType;

import javax.annotation.Nonnull;
import java.util.Set;

public final class BoldTagHandler extends BakedTagHandler {

    private static final String[] TAGS = new String[]{"bold", "b", "strong"};
    private static final Set<TagType> TYPES = Set.of(TagType.Open, TagType.Close);

    public static final BoldTagHandler INSTANCE = new BoldTagHandler();

    public BoldTagHandler() {
        super(TAGS, TYPES);
    }

    @Override
    public boolean handle(@Nonnull ParseContext context) {
        switch (context.getCurrentType()) {
            case Open -> {
                context.bold = true;
                return true;
            }
            case Close -> {
                context.bold = false;
                return true;
            }
        }
        return false;
    }
}
