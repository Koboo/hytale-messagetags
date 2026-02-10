package eu.koboo.messagetags.api.taghandler.types;

import com.hypixel.hytale.server.core.Message;
import eu.koboo.messagetags.api.taghandler.MessageBuilder;
import eu.koboo.messagetags.api.taghandler.TagAction;
import eu.koboo.messagetags.api.taghandler.TagHandler;

import javax.annotation.Nonnull;
import java.util.List;

public final class TranslationTagHandler extends TagHandler {

    public static final TranslationTagHandler INSTANCE = new TranslationTagHandler();

    private static final List<String> TAGS = List.of("translation", "lang");

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
        String translationKey = getArgument(root, argumentStart, argumentEnd);
        if (translationKey == null) {
            return false;
        }
        Message message = Message.translation(translationKey);
        // Translations can't use gradients
        // because they get resolved by the client.
        //state.createGradientMessage(translationKey);
        state.appendStyledMessage(message);
        return true;
    }
}
