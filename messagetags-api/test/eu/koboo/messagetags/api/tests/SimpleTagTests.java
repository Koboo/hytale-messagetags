package eu.koboo.messagetags.api.tests;

import static eu.koboo.messagetags.api.tests.TestUtils.assertMessage;

import eu.koboo.messagetags.api.MessageTags;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class SimpleTagTests {

    public record TestCase(String tagName, String argument, String expected) {
    }

    static Stream<TestCase> provide() {
        return Stream.of(
            new TestCase("bold", null, "{\"Children\": [{\"RawText\": \"Hello\", \"Bold\": true, \"Italic\": null, \"Monospace\": null, \"Underline\": null}, {\"RawText\": \" World!\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}"),
            new TestCase("underline", null, "{\"Children\": [{\"RawText\": \"Hello\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": true}, {\"RawText\": \" World!\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}"),
            new TestCase("italic", null, "{\"Children\": [{\"RawText\": \"Hello\", \"Bold\": null, \"Italic\": true, \"Monospace\": null, \"Underline\": null}, {\"RawText\": \" World!\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}"),
            new TestCase("monospace", null, "{\"Children\": [{\"RawText\": \"Hello\", \"Bold\": null, \"Italic\": null, \"Monospace\": true, \"Underline\": null}, {\"RawText\": \" World!\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}"),
            new TestCase("color", "#ffffff", "{\"Children\": [{\"RawText\": \"Hello\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#FFFFFF\"}, {\"RawText\": \" World!\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}"),
            new TestCase("color", "white", "{\"Children\": [{\"RawText\": \"Hello\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#FFFFFF\"}, {\"RawText\": \" World!\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}"),
            new TestCase("color", "&f", "{\"Children\": [{\"RawText\": \"Hello\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#FFFFFF\"}, {\"RawText\": \" World!\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}"),
            new TestCase("color", "§f", "{\"Children\": [{\"RawText\": \"Hello\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#FFFFFF\"}, {\"RawText\": \" World!\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}"),
            new TestCase("color", "255,255,255", "{\"Children\": [{\"RawText\": \"Hello\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#FFFFFF\"}, {\"RawText\": \" World!\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}"),
            new TestCase("link", "https://github.com/Koboo", "{\"Children\": [{\"RawText\": \"Hello\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Link\": \"https://github.com/Koboo\"}, {\"RawText\": \" World!\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}"),
            new TestCase("gradient", "blue:red:yellow", "{\"Children\": [{\"RawText\": \"H\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#5555FF\"}, {\"RawText\": \"e\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#AA55AA\"}, {\"RawText\": \"l\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#FF5555\"}, {\"RawText\": \"l\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#FFAA55\"}, {\"RawText\": \"o\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#FFFF55\"}, {\"RawText\": \" World!\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}"),
            new TestCase("transition", "blue:red:yellow:0.75", "{\"Children\": [{\"RawText\": \"Hello\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#FFAA55\"}, {\"RawText\": \" World!\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}")
        );
    }

    @ParameterizedTest
    @MethodSource("provide")
    public void test(TestCase testCase) {
        String openTag = "<" + testCase.tagName;
        if(testCase.argument != null && !testCase.argument.isEmpty()) {
            openTag = openTag + ":" + testCase.argument + ">";
        }  else {
            openTag = openTag + ">";
        }
        String tagText = openTag + "Hello</" + testCase.tagName + "> World!";
        assertMessage(testCase.expected, MessageTags.parse(tagText));
    }

    @Test
    public void test1() {
        String expected = "{\"Children\": [{\"RawText\": \"Aw\", \"Bold\": true, \"Italic\": null, \"Monospace\": null, \"Underline\": null}, {\"RawText\": \"e\", \"Bold\": true, \"Italic\": null, \"Monospace\": null, \"Underline\": true}, {\"RawText\": \"so\", \"Bold\": true, \"Italic\": null, \"Monospace\": null, \"Underline\": true, \"Color\": \"#FFAA00\"}, {\"RawText\": \"me \", \"Bold\": true, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#FFAA00\"}, {\"RawText\": \"test \", \"Bold\": true, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#FCBA03\"}, {\"RawText\": \"But every\", \"Bold\": true, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#AA0000\"}, {\"RawText\": \"thing \", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}, {\"RawText\": \"stripped\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": true}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}";
        String tagText = "<bold>Aw<ul>e<&6>so</ul>me <color:#fcba03>test <color:&4>But every<r>thing <underline>stripped";
        assertMessage(expected, MessageTags.parse(tagText));
    }

    @Test
    public void test2() {
        String expected = "{\"Children\": [{\"MessageId\": \"server.commands.help.desc\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#55FFFF\", \"Link\": \"https://github.com/Koboo\"}, {\"RawText\": \" Test \", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#55FFFF\"}, {\"RawText\": \"Translations\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#55FF55\"}, {\"RawText\": \"\\n\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}";
        String tagText = "<&b><link:https://github.com/Koboo><lang:server.commands.help.desc></link> Test <&a>Translations<linebreak>";
        assertMessage(expected, MessageTags.parse(tagText));
    }

}
