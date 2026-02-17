package eu.koboo.messagetags.api.taghandler.dynamic;

import com.hypixel.hytale.protocol.FormattedMessage;
import com.hypixel.hytale.server.core.Message;
import eu.koboo.messagetags.api.taghandler.DynamicTagHandler;
import eu.koboo.messagetags.api.taghandler.ParseContext;
import eu.koboo.messagetags.api.taghandler.TagType;
import eu.koboo.messagetags.api.variable.TagPlaceholder;

import javax.annotation.Nonnull;

public final class DynamicPlaceholderTagHandler extends DynamicTagHandler {

    public static final DynamicPlaceholderTagHandler INSTANCE = new DynamicPlaceholderTagHandler();

    @Override
    public boolean canHandle(@Nonnull ParseContext context) {
        if (context.getPlaceholderMap() == null || context.getPlaceholderMap().isEmpty()) {
            return false;
        }
        if (context.hasArguments()) {
            return false;
        }
        return context.isType(TagType.Open) || context.isType(TagType.Directive);
    }

    @Override
    public boolean handle(@Nonnull ParseContext context) {
        String tag = context.getCurrentTag();
        TagPlaceholder tagPlaceholder = context.getPlaceholderMap().get(tag);
        if (tagPlaceholder == null) {
            return false;
        }
        switch (tagPlaceholder.type()) {
            case RAW -> {
                Object value = tagPlaceholder.value();
                if (value == null) {
                    return false;
                }
                String stringValue = String.valueOf(value);
                context.appendStyledText(stringValue);
                return true;
            }
            case MESSAGE -> {
                Object value = tagPlaceholder.value();
                if (value instanceof Message message) {
                    context.appendMessage(message.getFormattedMessage());
                    return true;
                }
                if (value instanceof FormattedMessage message) {
                    context.appendMessage(message);
                    return true;
                }
                return false;
            }
            case PARSABLE -> {
                Object value = tagPlaceholder.value();
                if (value == null) {
                    return false;
                }
                String stringValue = String.valueOf(value);
                Message parsed = context.getParser().parse(stringValue, context.strip);
                FormattedMessage message = parsed.getFormattedMessage();
                FormattedMessage[] children = message.children;
                if (children == null) {
                    return false;
                }
                int childrenLength = children.length;
                if (childrenLength == 0) {
                    return false;
                }
                for (int i = 0; i < childrenLength; i++) {
                    FormattedMessage child = children[i];
                    context.appendMessage(child);
                }
                return true;
            }
        }
        return false;
    }
}
