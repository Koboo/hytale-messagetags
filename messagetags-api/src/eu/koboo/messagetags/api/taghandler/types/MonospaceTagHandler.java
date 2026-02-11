package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;

public final class MonospaceTagHandler extends TagHandler {

    public static final MonospaceTagHandler INSTANCE = new MonospaceTagHandler();

    private static final String[] TAGS = new String[]{"monospace", "mono"};

    @Override
    public boolean canHandle(@Nonnull MessageBuilder state, int nameStart, int nameEnd) {
        return hasTagOf(TAGS, state.getInputText(), nameStart, nameEnd);
    }

    @Override
    public boolean handle(@Nonnull MessageBuilder state,
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
