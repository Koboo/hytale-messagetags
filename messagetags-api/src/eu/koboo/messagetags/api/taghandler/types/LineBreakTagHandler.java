package eu.koboo.messagetags.api.taghandler.types;

import com.hypixel.hytale.protocol.FormattedMessage;
import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagType;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;

public final class LineBreakTagHandler extends TagHandler {

    public static final LineBreakTagHandler INSTANCE = new LineBreakTagHandler();

    private static final String[] TAGS = new String[]{"linebreak", "br"};
    private static final FormattedMessage LINE_SEPARATOR;

    static {
        LINE_SEPARATOR = new FormattedMessage();
        LINE_SEPARATOR.rawText = "\n";
    }

    @Override
    public boolean canHandle(@Nonnull ParseContext context) {
        if(!context.isType(TagType.Open) && !context.isType(TagType.Directive)) {
            return false;
        }
        return context.hasTagOf(TAGS);
    }

    @Override
    public boolean handle(@Nonnull ParseContext context) {
        // Since then does a line separator need styling? O_o
        //state.applyStyleTo(LINE_SEPARATOR);
        context.appendMessage(LINE_SEPARATOR);
        return true;
    }
}
