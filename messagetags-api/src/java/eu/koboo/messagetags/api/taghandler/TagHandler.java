package eu.koboo.messagetags.api.taghandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class TagHandler {

    public abstract boolean canHandle(
        @Nonnull String root,
        int nameStart,
        int nameEnd
    );

    public abstract boolean handle(
        @Nonnull MessageBuilder state,
        @Nonnull String root,
        int nameStart,
        int nameEnd,
        int argumentStart,
        int argumentEnd,
        @Nonnull TagAction action
    );

    protected static String getTag(@Nonnull String root, int start, int end) {
        return root.substring(start, end);
    }

    protected static boolean equalsIgnoreCase(
        @Nonnull String string,
        @Nonnull String root, int start, int end) {

        // Start is bigger than or equal to the end? Can't be correct.
        if (start >= end) {
            return false;
        }

        // Check if the given root and the text have equal length
        int checkedLength = end - start;
        if (checkedLength != string.length()) {
            return false;
        }

        // Is one of both strings empty? If so, we don't need to iterate.
        if (root.isEmpty() || string.isEmpty()) {
            return false;
        }

        for (int rootIndex = 0; rootIndex < checkedLength; rootIndex++) {
            char rootChar = Character.toLowerCase(root.charAt(start + rootIndex));
            char tagChar = Character.toLowerCase(string.charAt(rootIndex));
            if (rootChar != tagChar) {
                return false;
            }
        }
        return true;
    }

    protected static boolean hasTagOf(
        @Nonnull List<String> tagList,
        @Nonnull String root,
        int start, int end) {
        for (String tag : tagList) {
            if (!equalsIgnoreCase(tag, root, start, end)) {
                continue;
            }
            return true;
        }
        return false;
    }

    @Nullable
    protected static String getArgument(@Nonnull String root, int argumentStart, int argumentEnd) {
        if (argumentStart == -1 || argumentEnd == -1) {
            return null;
        }
        if (argumentStart >= argumentEnd) {
            return null;
        }
        String argument = root.substring(argumentStart, argumentEnd).trim();
        if (argument.isEmpty()) {
            return null;
        }
        return argument;
    }
}