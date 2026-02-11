package eu.koboo.messagetags.api.taghandler.types;

import com.hypixel.hytale.protocol.FormattedMessage;
import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;
import java.util.List;

public final class LineBreakTagHandler extends TagHandler {

    public static final LineBreakTagHandler INSTANCE = new LineBreakTagHandler();

    private static final String[] TAGS = new String[]{"linebreak", "br"};
    private static final FormattedMessage LINE_SEPARATOR;

    static {
        LINE_SEPARATOR = new FormattedMessage();
        LINE_SEPARATOR.rawText = "\n";
    }

    @Override
    public boolean canHandle(@Nonnull MessageBuilder state, int nameStart, int nameEnd) {
        return hasTagOf(TAGS, state.getInputText(), nameStart, nameEnd);
    }

    @Override
    public boolean handle(@Nonnull MessageBuilder state,
                          int nameStart, int nameEnd,
                          int argumentStart, int argumentEnd,
                          @Nonnull TagAction action) {
        if (action != TagAction.Open && action != TagAction.Directive) {
            return false;
        }
        // Since then does a line separator need styling? O_o
        //state.applyStyleTo(LINE_SEPARATOR);
        state.appendMessage(LINE_SEPARATOR);
        return true;
    }
}
