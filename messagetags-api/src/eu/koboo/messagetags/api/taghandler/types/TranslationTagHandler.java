package eu.koboo.messagetags.api.taghandler.types;

import com.hypixel.hytale.protocol.FormattedMessage;
import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;

public final class TranslationTagHandler extends TagHandler {

    public static final TranslationTagHandler INSTANCE = new TranslationTagHandler();

    private static final String[] TAGS = new String[]{"translation", "lang"};

    @Override
    public boolean canHandle(@Nonnull MessageBuilder state, int nameStart, int nameEnd, @Nonnull TagAction action) {
        if(!state.isType(TagAction.Open) && !state.isType(TagAction.Directive)) {
            return false;
        }
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
        String translationKey = state.getArgument();
        if (translationKey == null) {
            return false;
        }
        FormattedMessage message = state.createByTranslation(translationKey);
        // Translations can't use gradients because they get resolved by the client.
        //state.createGradientMessage(translationKey);
        state.appendStyledMessage(message);
        return true;
    }
}
