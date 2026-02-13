package eu.koboo.messagetags.api.taghandler.types;

import com.hypixel.hytale.protocol.FormattedMessage;
import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagHandler;
import eu.koboo.messagetags.api.taghandler.TagType;

import javax.annotation.Nonnull;

public final class TranslationTagHandler extends TagHandler {

    public static final TranslationTagHandler INSTANCE = new TranslationTagHandler();

    private static final String[] TAGS = new String[]{"translation", "lang"};

    @Override
    public boolean canHandle(@Nonnull ParseContext context) {
        if (!context.isType(TagType.Open) && !context.isType(TagType.Directive)) {
            return false;
        }
        return context.hasTagOf(TAGS);
    }

    @Override
    public boolean handle(@Nonnull ParseContext context) {
        String translationKey = context.getArgument();
        if (translationKey == null) {
            return false;
        }
        FormattedMessage message = context.createByTranslation(translationKey);
        // Translations can't use gradients because they get resolved by the client.
        //state.createGradientMessage(translationKey);
        context.appendStyledMessage(message);
        return true;
    }
}
