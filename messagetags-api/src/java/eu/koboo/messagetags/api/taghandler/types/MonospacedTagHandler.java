package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;
import java.util.List;

public final class MonospacedTagHandler extends TagHandler {

    public static final MonospacedTagHandler INSTANCE = new MonospacedTagHandler();

    private static final List<String> TAGS = List.of("monospaced", "monospace", "mono", "ms");

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
                state.monospaced = true;
                return true;
            }
            case Close -> {
                state.monospaced = false;
                return true;
            }
        }
        return false;
    }
}
