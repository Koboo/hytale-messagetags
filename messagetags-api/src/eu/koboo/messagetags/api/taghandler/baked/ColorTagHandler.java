package eu.koboo.messagetags.api.taghandler.baked;

import eu.koboo.messagetags.api.taghandler.BakedTagHandler;
import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagType;

import javax.annotation.Nonnull;
import java.util.Set;

public final class ColorTagHandler extends BakedTagHandler {

    private static final String[] TAGS = new String[]{"color", "colour", "c"};
    private static final Set<TagType> TYPES = Set.of(TagType.Open, TagType.Close);

    public static final ColorTagHandler INSTANCE = new ColorTagHandler();

    public ColorTagHandler() {
        super(TAGS, TYPES);
    }

    @Override
    public boolean handle(@Nonnull ParseContext context) {
        switch (context.getCurrentType()) {
            case Open -> {
                String argument = context.getArgument();
                if (argument == null) {
                    return false;
                }
                String color = context.parser.parseColor(argument);
                if (color == null) {
                    return false;
                }
                context.color = color;
                return true;
            }
            case Close -> {
                context.color = null;
                return true;
            }
        }
        return false;
    }
}
