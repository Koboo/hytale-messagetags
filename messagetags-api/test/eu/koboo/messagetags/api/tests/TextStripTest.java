package eu.koboo.messagetags.api.tests;

import static eu.koboo.messagetags.api.tests.TestUtils.assertStripped;

import eu.koboo.messagetags.api.MessageTags;
import org.junit.jupiter.api.Test;

public class TextStripTest {

    @Test
    public void test1() {
        String expected = "Awesome test But everything stripped";
        String tagText = "<bold>Aw<ul>e<&6>so</ul>me <color:#fcba03>test <color:&4>But every<r>thing <underline>stripped";
        assertStripped(expected, MessageTags.stripToString(tagText));
    }

    @Test
    public void test2() {
        String expected = "server.commands.help.desc Test Translations\n";
        String tagText = "<&b><link:https://github.com/Koboo><lang:server.commands.help.desc></link> Test <&a>Translations<linebreak>";
        assertStripped(expected, MessageTags.stripToString(tagText));
    }

}
