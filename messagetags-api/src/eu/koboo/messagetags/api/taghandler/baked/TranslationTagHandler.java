package eu.koboo.messagetags.api.taghandler.baked;

import com.hypixel.hytale.protocol.FormattedMessage;
import eu.koboo.messagetags.api.taghandler.BakedTagHandler;
import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagType;

import javax.annotation.Nonnull;
import java.util.Set;

public final class TranslationTagHandler extends BakedTagHandler {

    private static final String[] TAGS = new String[]{"translation", "lang"};
    private static final Set<TagType> TYPES = Set.of(TagType.Open, TagType.Directive);

    public static final TranslationTagHandler INSTANCE = new TranslationTagHandler();

    public TranslationTagHandler() {
        super(TAGS, TYPES);
    }

    @Override
    public boolean handle(@Nonnull ParseContext context) {
        String translationKey = context.getArgument();
        if (translationKey == null) {
            return false;
        }
        FormattedMessage message = context.createByTranslation(translationKey);
        context.appendMessage(message);
        return true;
    }
}
