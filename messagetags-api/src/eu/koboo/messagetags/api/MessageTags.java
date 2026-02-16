package eu.koboo.messagetags.api;

import com.hypixel.hytale.codec.ExtraInfo;
import com.hypixel.hytale.server.core.Message;
import eu.koboo.messagetags.api.color.NamedColor;
import eu.koboo.messagetags.api.taghandler.BakedTagHandler;
import eu.koboo.messagetags.api.taghandler.DynamicTagHandler;
import eu.koboo.messagetags.api.variable.TagPlaceholder;
import org.bson.BsonValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class MessageTags {

    /**
     * The static default {@link MessageParser} instance.
     */
    private static final MessageParser INSTANCE = new MessageParser();

    /**
     * Uses the given String and parses it to Hytale's {@link Message} object.
     *
     * @param text The text to parse.
     * @return The parsed {@link Message}.
     */
    @Nonnull
    public static Message parse(@Nullable String text, @Nullable TagPlaceholder... placeholders) {
        return INSTANCE.parse(text, false, placeholders);
    }

    /**
     * Uses the given String and strips away every tag, color, and formatting
     * and returns the same structure of {@link Message} as the parse method.
     *
     * @param text The text to strip.
     * @return The parsed {@link Message}.
     */
    @Nonnull
    public static Message strip(@Nullable String text, @Nullable TagPlaceholder... placeholders) {
        return INSTANCE.parse(text, true, placeholders);
    }

    /**
     * Uses the given String and strips away every tag, color, and formatting
     * and returns a colorless simplified {@link String} instead of a {@link Message}.
     *
     * @param text The text to strip.
     * @return The parsed and simplified {@link String}.
     */
    @Nullable
    public static String stripToString(@Nullable String text, @Nullable TagPlaceholder... placeholders) {
        Message message = INSTANCE.parse(text, true, placeholders);
        return INSTANCE.stripRawText(message);
    }

    /**
     * Register a non-default {@link DynamicTagHandler} to the global {@link MessageParser} instance.
     *
     * @param dynamicTagHandler The {@link DynamicTagHandler} instance to register.
     */
    public static void registerDynamicTagHandler(@Nonnull DynamicTagHandler dynamicTagHandler) {
        INSTANCE.registerDynamicTagHandler(dynamicTagHandler);
    }

    /**
     * Register a non-default {@link BakedTagHandler} to the global {@link MessageParser} instance.
     *
     * @param bakedTagHandler The {@link BakedTagHandler} instance to register.
     */
    public static void registerBakedTagHandler(@Nonnull BakedTagHandler bakedTagHandler) {
        INSTANCE.registerBakedTagHandler(bakedTagHandler);
    }

    /**
     * Register a non-default {@link NamedColor} to the global {@link MessageParser} instance.
     *
     * @param namedColor The {@link NamedColor} instance to register.
     */
    public static void registerNamedColor(@Nonnull NamedColor namedColor) {
        INSTANCE.registerNamedColor(namedColor);
    }

    /**
     * Creates a new {@link MessageParser} instance.
     *
     * @return The newly created {@link MessageParser} instance.
     */
    @Nonnull
    public static MessageParser createParser() {
        return new MessageParser();
    }

    /**
     * Converts a {@link Message} object to it's JSON representation string.
     *
     * @param message The {@link Message} object to convert.
     * @return The JSON representation string.
     */
    @Nullable
    public static String toJson(@Nullable Message message) {
        if (message == null) {
            return null;
        }
        BsonValue encode = Message.CODEC.encode(message, new ExtraInfo());
        return encode.toString();
    }
}
