package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.colors.ColorParser;
import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;

public final class DynamicColorTagHandler extends TagHandler {

    public static final DynamicColorTagHandler INSTANCE = new DynamicColorTagHandler();

    @Override
    public boolean canHandle(@Nonnull String root, int nameStart, int nameEnd) {
        String tag = getTag(root, nameStart, nameEnd);
        String colorHexCode = ColorParser.parseColor(tag);
        return colorHexCode != null;
    }

    @Override
    public boolean handle(@Nonnull MessageBuilder state,
                          @Nonnull String root,
                          int nameStart, int nameEnd,
                          int argumentStart, int argumentEnd,
                          @Nonnull TagAction action) {
        if (action != TagAction.Open && action != TagAction.Directive) {
            return false;
        }
        String tag = getTag(root, nameStart, nameEnd);
        String colorHexCode = ColorParser.parseColor(tag);
        if (colorHexCode == null) {
            return false;
        }
        state.color = colorHexCode;
        return true;
    }
}
