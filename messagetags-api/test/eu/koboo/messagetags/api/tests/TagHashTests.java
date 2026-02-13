package eu.koboo.messagetags.api.tests;

import eu.koboo.messagetags.api.MessageParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class TagHashTests {

    @Test
    public void test() {
        Set<Integer> tags = new HashSet<>();
        assertHash(tags, "bold", false);
        assertHash(tags, "bold", true);
        assertHash(tags, "italic", false);
        assertHash(tags, "mono", false);
        assertHash(tags, "color", false);
        assertHash(tags, "color", true);
        assertHash(tags, "colour", false);
        assertHash(tags, "newline", false);
    }

    private static void assertHash(Set<Integer> hashCodeSet, String tag, boolean expectContain) {
        int tagHash = MessageParser.createStringHashCode(tag, 0, tag.length());
        System.out.println(tag + " -> " + tagHash + " (" + hashCodeSet.contains(tagHash) + ")");
        if (expectContain) {
            Assertions.assertTrue(hashCodeSet.contains(tagHash));
        } else {
            Assertions.assertFalse(hashCodeSet.contains(tagHash));
        }
        hashCodeSet.add(tagHash);
    }
}
