package eu.koboo.messagetags.api;

import com.hypixel.hytale.codec.ExtraInfo;
import com.hypixel.hytale.server.core.Message;
import eu.koboo.messagetags.api.taghandler.TagHandler;
import org.bson.BsonValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class MessageTags {

    private static final MessageParser INSTANCE = new MessageParser();

    @Nonnull
    public static Message parse(@Nullable String text) {
        return INSTANCE.parse(text, false);
    }

    @Nonnull
    public static Message strip(@Nullable String text) {
        return INSTANCE.parse(text, true);
    }

    public static void registerTagHandler(@Nonnull TagHandler tagHandler) {
        INSTANCE.registerTagHandler(tagHandler);
    }

    @Nonnull
    public static MessageParser createParser() {
        return new MessageParser();
    }

    @Nullable
    public static String toJson(@Nullable Message message) {
        if(message == null) {
            return null;
        }
        BsonValue encode = Message.CODEC.encode(message, new ExtraInfo());
        return encode.toString();
    }
}
