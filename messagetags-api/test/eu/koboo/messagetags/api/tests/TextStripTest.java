package eu.koboo.messagetags.api.tests;

import eu.koboo.messagetags.api.MessageTags;
import org.junit.jupiter.api.Test;

import static eu.koboo.messagetags.api.tests.TestUtils.assertStripped;

public class TextStripTest {

    @Test
    public void test1() {
        String expected = "Awesome test But everything stripped";
        String tagText = "<bold>Aw<ul>eso</ul>me <color:#fcba03>test <color:dark_red>But every<r>thing <underline>stripped";
        assertStripped(expected, MessageTags.stripToString(tagText));
    }

    @Test
    public void test2() {
        String expected = "server.commands.help.desc Test Translations\n";
        String tagText = "<aqua><link:https://github.com/Koboo><lang:server.commands.help.desc></link> Test <green>Translations<linebreak>";
        assertStripped(expected, MessageTags.stripToString(tagText));
    }

    @Test
    public void test3() {
        String expected = "";
        String tagText = "<link:https://github.com/Koboo>Testing strip of links</link>";
        assertStripped(expected, MessageTags.stripToString(tagText));
    }

    @Test
    public void test4() {
        String expected = "\n";
        String tagText = "<linebreak>";
        assertStripped(expected, MessageTags.stripToString(tagText));
    }

    @Test
    public void test5() {
        String expected = "Legacy color test";
        String tagText = "<gold>Leg&4acy c§folor t&rest";
        assertStripped(expected, MessageTags.stripToString(tagText));
    }

    @Test
    public void test6() {
        String expected = "<bol>Leg&jac<unrline>y color <color:TT>test<liebreak>";
        String tagText = "<bol>L</bold>eg&jac<unrline>y color <color:TT>test<liebreak>";
        assertStripped(expected, MessageTags.stripToString(tagText));
    }
}
