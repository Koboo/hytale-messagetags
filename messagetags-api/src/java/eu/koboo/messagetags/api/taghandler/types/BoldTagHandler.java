package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;
import java.util.List;

public final class BoldTagHandler extends TagHandler {

    public static final BoldTagHandler INSTANCE = new BoldTagHandler();

    private static final List<String> TAGS = List.of("bold", "b");

    @Override
    public boolean canHandle(@Nonnull String root, int nameStart, int nameEnd) {
        return hasTagOf(TAGS, root, nameStart, nameEnd);
    }

    @Override
    public boolean handle(@Nonnull MessageBuilder state,
                          @Nonnull String root,
                          int nameStart, int nameEnd,
                          int argumentStart, int argumentEnd,
                          @Nonnull TagAction action) {
        switch (action) {
            case Open -> {
                state.bold = true;
                return true;
            }
            case Close -> {
                state.bold = false;
                return true;
            }
        }
        return false;
    }
}
