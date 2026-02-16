package eu.koboo.messagetags.api.taghandler.baked;

import eu.koboo.messagetags.api.taghandler.BakedTagHandler;
import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagType;

import javax.annotation.Nonnull;
import java.util.Set;

public final class MonospaceTagHandler extends BakedTagHandler {

    private static final String[] TAGS = new String[]{"monospace", "mono", "monospaced"};
    private static final Set<TagType> TYPES = Set.of(TagType.Open, TagType.Close);

    public static final MonospaceTagHandler INSTANCE = new MonospaceTagHandler();

    public MonospaceTagHandler() {
        super(TAGS, TYPES);
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
