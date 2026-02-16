package eu.koboo.messagetags.api.variable;

import com.hypixel.hytale.protocol.FormattedMessage;
import com.hypixel.hytale.server.core.Message;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public record TagPlaceholder(String name, Object value, Type type) {

    public TagPlaceholder {
        if (name == null) {
            throw new NullPointerException("Name cannot be null!");
        }
        name = name.trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        if (name.startsWith("<")) {
            name = name.substring(1);
        }
        if (name.endsWith(">")) {
            name = name.substring(0, name.length() - 1);
//            name = name + ">";
        }
    }

    public static TagPlaceholder parse(@Nonnull String name, @Nullable String value) {
        return new TagPlaceholder(name, value, Type.PARSABLE);
    }

    public static TagPlaceholder raw(@Nonnull String name, @Nullable Object value) {
        return new TagPlaceholder(name, value, Type.RAW);
    }

    public static TagPlaceholder message(@Nonnull String name, @Nullable Message value) {
        return new TagPlaceholder(name, value, Type.MESSAGE);
    }

    public static TagPlaceholder formatted(@Nonnull String name, @Nullable FormattedMessage value) {
        return new TagPlaceholder(name, value, Type.MESSAGE);
    }

    public enum Type {
        RAW, MESSAGE, PARSABLE
    }
}
