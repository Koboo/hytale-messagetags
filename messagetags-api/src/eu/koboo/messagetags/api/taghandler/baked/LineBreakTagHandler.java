package eu.koboo.messagetags.api.taghandler.baked;

import com.hypixel.hytale.protocol.FormattedMessage;
import eu.koboo.messagetags.api.taghandler.BakedTagHandler;
import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagType;

import javax.annotation.Nonnull;
import java.util.Set;

public final class LineBreakTagHandler extends BakedTagHandler {

    private static final String[] TAGS = new String[]{"linebreak", "br", "newline"};
    private static final Set<TagType> TYPES = Set.of(TagType.Open, TagType.Directive);
    private static final FormattedMessage LINE_SEPARATOR;

    static {
        LINE_SEPARATOR = new FormattedMessage();
        LINE_SEPARATOR.rawText = "\n";
    }

    public static final LineBreakTagHandler INSTANCE = new LineBreakTagHandler();

    public LineBreakTagHandler() {
        super(TAGS, TYPES);
    }

    @Override
    public boolean handle(@Nonnull ParseContext context) {
        context.appendStyledMessage(LINE_SEPARATOR);
        return true;
    }
}
