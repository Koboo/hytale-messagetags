package eu.koboo.messagetags.api.taghandler;

import javax.annotation.Nonnull;
import java.util.Set;

public abstract class BakedTagHandler {

    public final String[] handleTags;
    public final Set<TagType> handleTypeSet;

    public BakedTagHandler(String[] handleTags, Set<TagType> handleTypeSet) {
        if (handleTags == null) {
            throw new NullPointerException("handleTags is null!");
        }
        if (handleTags.length == 0) {
            throw new IllegalArgumentException("handleTags is empty!");
        }
        this.handleTags = handleTags;
        if (handleTypeSet == null) {
            throw new NullPointerException("handleTypes is null!");
        }
        if (handleTypeSet.isEmpty()) {
            throw new IllegalArgumentException("handleTypes is empty!");
        }
        this.handleTypeSet = handleTypeSet;
    }

    public abstract boolean handle(@Nonnull ParseContext context);
}