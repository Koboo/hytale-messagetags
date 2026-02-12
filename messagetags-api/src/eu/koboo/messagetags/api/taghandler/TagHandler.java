package eu.koboo.messagetags.api.taghandler;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class TagHandler {

    public abstract boolean canHandle(
        @Nonnull MessageBuilder state,
        int nameStart,
        int nameEnd
    );

    public abstract boolean handle(
        @Nonnull MessageBuilder state,
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

        for (int rootIndex = 0; rootIndex < checkedLength; rootIndex++) {
            char rootChar = toLowerAscii(root.charAt(start + rootIndex));
            char tagChar = toLowerAscii(string.charAt(rootIndex));
            if (rootChar != tagChar) {
                return false;
            }
        }
        return true;
    }

    protected static boolean hasTagOf(
        @Nonnull String[] tagList,
        @Nonnull String root,
        int start, int end) {
        int length = tagList.length;
        for (int i = 0; i < length; i++) {
            String tag = tagList[i];
            if (equalsIgnoreCase(tag, root, start, end)) {
                return true;
            }
        }
        return false;
    }

    private static char toLowerAscii(char c) {
        return (c >= 'A' && c <= 'Z') ? (char)(c | 0x20) : c;
    }
}