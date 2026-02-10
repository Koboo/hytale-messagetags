package eu.koboo.messagetags.api.taghandler.types;

import com.hypixel.hytale.server.core.Message;
import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;
import java.util.List;

public final class LineBreakTagHandler extends TagHandler {

    public static final LineBreakTagHandler INSTANCE = new LineBreakTagHandler();

    private static final List<String> TAGS = List.of("newline", "linebreak", "br");
    private static final Message LINE_SEPARATOR = Message.raw("\n");

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
        if (action != TagAction.Open && action != TagAction.Directive) {
            return false;
        }
        // Since then does a line separator need styling? O_o
        //state.applyStyleTo(LINE_SEPARATOR);
        state.getRootMessage().insert(LINE_SEPARATOR);
        return true;
    }
}
