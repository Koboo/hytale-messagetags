package eu.koboo.messagetags.api.variable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public record TagVariable(String name, String value) {

    public TagVariable {
        if (name == null) {
            throw new NullPointerException("Name cannot be null!");
        }
        name = name.trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        if (!name.startsWith("<")) {
            name = "<" + name;
        }
        if (!name.endsWith(">")) {
            name = name + ">";
        }
    }

    public static TagVariable of(@Nonnull String name, @Nullable String value) {
        return new TagVariable(name, value);
    }
}
