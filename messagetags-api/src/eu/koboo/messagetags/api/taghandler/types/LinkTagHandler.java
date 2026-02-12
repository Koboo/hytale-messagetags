package eu.koboo.messagetags.api.taghandler.types;

import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;

public final class LinkTagHandler extends TagHandler {

    public static final LinkTagHandler INSTANCE = new LinkTagHandler();

    private static final String[] TAGS = new String[]{"link", "url"};

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
                String link = state.getArgument(argumentStart, argumentEnd);
                if (link == null) {
                    return false;
                }
                state.link = link;
                return true;
            }
            case Close -> {
                state.link = null;
                return true;
            }
        }
        return false;
    }
}
