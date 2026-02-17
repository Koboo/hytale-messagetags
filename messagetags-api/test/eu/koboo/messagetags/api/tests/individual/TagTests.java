package eu.koboo.messagetags.api.tests.individual;

import com.hypixel.hytale.server.core.Message;
import eu.koboo.messagetags.api.MessageTags;
import eu.koboo.messagetags.api.tests.AbstractTest;
import eu.koboo.messagetags.api.tests.BeautifyJson;
import eu.koboo.messagetags.api.variable.TagPlaceholder;
import org.junit.jupiter.api.Test;

public class TagTests extends AbstractTest {

    @Test
    public void test1() {
        String expected = "{\"Children\": [{\"RawText\": \"Aw\", \"Bold\": true, \"Italic\": null, \"Monospace\": null, \"Underline\": null}, {\"RawText\": \"e\", \"Bold\": true, \"Italic\": null, \"Monospace\": null, \"Underline\": true}, {\"RawText\": \"so\", \"Bold\": true, \"Italic\": null, \"Monospace\": null, \"Underline\": true, \"Color\": \"#ffaa00\"}, {\"RawText\": \"me \", \"Bold\": true, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#ffaa00\"}, {\"RawText\": \"test \", \"Bold\": true, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#fcba03\"}, {\"RawText\": \"But every\", \"Bold\": true, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#aa0000\"}, {\"RawText\": \"thing \", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}, {\"RawText\": \"stripped\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": true}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}";
        String tagText = "<bold>Aw<ul>e<gold>so</ul>me <color:#fcba03>test <color:dark_red>But every<r>thing <underline>stripped";
        assertMessage(expected, MessageTags.parse(tagText));
    }

    @Test
    public void test2() {
        String expected = "{\"Children\": [{\"MessageId\": \"server.commands.help.desc\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#55ffff\", \"Link\": \"https://github.com/Koboo\"}, {\"RawText\": \" Test \", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#55ffff\"}, {\"RawText\": \"Translations\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#55ff55\"}, {\"RawText\": \"\\n\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}";
        String tagText = "<aqua><link:https://github.com/Koboo><lang:server.commands.help.desc></link> Test <green>Translations<linebreak>";
        assertMessage(expected, MessageTags.parse(tagText));
    }

    @Test
    public void test3() {
        String expected = "{\"Children\": [{\"RawText\": \"Leg\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#ffaa00\"}, {\"RawText\": \"acy c\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#aa0000\"}, {\"RawText\": \"olor t\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#ffffff\"}, {\"RawText\": \"est\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}";
        String tagText = "<gold>Leg&4acy c§folor t&rest";
        assertMessage(expected, MessageTags.parse(tagText));
    }

    @Test
    public void test4() {
        String expected = "{\"Children\": [{\"RawText\": \"Bold\", \"Bold\": true, \"Italic\": null, \"Monospace\": null, \"Underline\": null}, {\"RawText\": \"NotBold\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}, {\"RawText\": \"Red\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#aa0000\"}, {\"RawText\": \"RedUnderlined\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": true, \"Color\": \"#aa0000\"}, {\"RawText\": \"WhiteUnderlined\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": true, \"Color\": \"#ffffff\"}, {\"RawText\": \"Bold\", \"Bold\": true, \"Italic\": null, \"Monospace\": null, \"Underline\": true, \"Color\": \"#ffffff\"}, {\"RawText\": \"Reset\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}, {\"RawText\": \"\\n\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}";
        String tagText = "<bold>Bold</bold>NotBold&4Red<underline>RedUnderlined§fWhiteUnderlined<bold>Bold&rReset<linebreak>";
        assertMessage(expected, MessageTags.parse(tagText));
    }

    @Test
    public void test5() {
        String expected = "{\"Children\": [{\"RawText\": \"Hello World!\", \"Bold\": true, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}";
        String tagText = "<bold><text></bold>";
        assertMessage(expected, MessageTags.parse(tagText, TagPlaceholder.raw("text", "Hello World!")));
    }

    @Test
    public void test6() {
        String expected = "{\"Children\": [{\"RawText\": \"Admin \", \"Bold\": true, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#ff5555\"}, {\"RawText\": \"Koboo\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}, {\"RawText\": \" Clan\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#55ffff\"}, {\"RawText\": \": \", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}, {\"RawText\": \"Hello World!\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}";
        String tagText = "<prefix><username><suffix>: <message>";
        assertMessage(expected, MessageTags.parse(tagText,
            TagPlaceholder.parse("prefix", "<red><bold>Admin "),
            TagPlaceholder.parse("suffix", "<aqua> Clan"),
            TagPlaceholder.raw("username", "Koboo"),
            TagPlaceholder.message("message", Message.raw("Hello World!"))
        ));
    }

}
