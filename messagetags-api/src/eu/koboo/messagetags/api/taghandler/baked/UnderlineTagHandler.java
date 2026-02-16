package eu.koboo.messagetags.api.taghandler.baked;

import eu.koboo.messagetags.api.taghandler.BakedTagHandler;
import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagType;

import javax.annotation.Nonnull;
import java.util.Set;

public final class UnderlineTagHandler extends BakedTagHandler {

    private static final String[] TAGS = new String[]{"underline", "ul", "underlined"};
    private static final Set<TagType> TYPES = Set.of(TagType.Open, TagType.Close);

    public static final UnderlineTagHandler INSTANCE = new UnderlineTagHandler();

    public UnderlineTagHandler() {
        super(TAGS, TYPES);
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
