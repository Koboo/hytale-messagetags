package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagType;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;

public final class ColorTagHandler extends TagHandler {

    public static final ColorTagHandler INSTANCE = new ColorTagHandler();

    private static final String[] TAGS = new String[]{"color", "colour", "c"};

    @Override
    public boolean canHandle(@Nonnull ParseContext context) {
        if(!context.isType(TagType.Open) && !context.isType(TagType.Close)) {
            return false;
        }
        return context.hasTagOf(TAGS);
    }

    @Override
    public boolean handle(@Nonnull ParseContext context) {
        switch (context.getCurrentType()) {
            case Open -> {
                String argument = context.getArgument();
                if (argument == null) {
                    return false;
                }
                String color = context.parseColor(argument);
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
