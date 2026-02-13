package eu.koboo.messagetags.api.tests;

import eu.koboo.messagetags.api.MessageTags;
import eu.koboo.messagetags.api.variable.TagVariable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static eu.koboo.messagetags.api.tests.TestUtils.assertMessage;

public class SimpleTagTests {

    public record TestCase(String tagName, String argument, String expected) {
    }

    static Stream<TestCase> provide() {
        return Stream.of(
            new TestCase("bold", null, "{\"Children\": [{\"RawText\": \"Hello\", \"Bold\": true, \"Italic\": null, \"Monospace\": null, \"Underline\": null}, {\"RawText\": \" World!\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}"),
            new TestCase("underline", null, "{\"Children\": [{\"RawText\": \"Hello\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": true}, {\"RawText\": \" World!\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}"),
            new TestCase("italic", null, "{\"Children\": [{\"RawText\": \"Hello\", \"Bold\": null, \"Italic\": true, \"Monospace\": null, \"Underline\": null}, {\"RawText\": \" World!\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}"),
            new TestCase("monospace", null, "{\"Children\": [{\"RawText\": \"Hello\", \"Bold\": null, \"Italic\": null, \"Monospace\": true, \"Underline\": null}, {\"RawText\": \" World!\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}"),
            new TestCase("color", "#ffffff", "{\"Children\": [{\"RawText\": \"Hello\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#ffffff\"}, {\"RawText\": \" World!\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}"),
            new TestCase("color", "white", "{\"Children\": [{\"RawText\": \"Hello\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#ffffff\"}, {\"RawText\": \" World!\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}"),
            new TestCase("link", "https://github.com/Koboo", "{\"Children\": [{\"RawText\": \"Hello\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Link\": \"https://github.com/Koboo\"}, {\"RawText\": \" World!\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}"),
            new TestCase("gradient", "blue:red:yellow", "{\"Children\": [{\"RawText\": \"H\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#5555FF\"}, {\"RawText\": \"e\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#AA55AA\"}, {\"RawText\": \"l\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#FF5555\"}, {\"RawText\": \"l\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#FFAA55\"}, {\"RawText\": \"o\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#FFFF55\"}, {\"RawText\": \" World!\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}"),
            new TestCase("transition", "blue:red:yellow:0.75", "{\"Children\": [{\"RawText\": \"Hello\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#FFAA55\"}, {\"RawText\": \" World!\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}")
        );
    }

    @ParameterizedTest
    @MethodSource("provide")
    public void test(TestCase testCase) {
        String openTag = "<" + testCase.tagName;
        if (testCase.argument != null && !testCase.argument.isEmpty()) {
            openTag = openTag + ":" + testCase.argument + ">";
        } else {
            openTag = openTag + ">";
        }
        String tagText = openTag + "Hello</" + testCase.tagName + "> World!";
        assertMessage(testCase.expected, MessageTags.parse(tagText));
    }

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
        String expected = "{\"Children\": [{\"RawText\": \"L\", \"Bold\": true, \"Italic\": null, \"Monospace\": null, \"Underline\": null}, {\"RawText\": \"eg\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}, {\"RawText\": \"ac\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null, \"Color\": \"#aa0000\"}, {\"RawText\": \"y c\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": true, \"Color\": \"#aa0000\"}, {\"RawText\": \"olor \", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": true, \"Color\": \"#ffffff\"}, {\"RawText\": \"t\", \"Bold\": true, \"Italic\": null, \"Monospace\": null, \"Underline\": true, \"Color\": \"#ffffff\"}, {\"RawText\": \"est\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}, {\"RawText\": \"\\n\", \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}";
        String tagText = "<bold>L</bold>eg&4ac<underline>y c§folor <bold>t&rest<linebreak>";
        assertMessage(expected, MessageTags.parse(tagText));
    }

    @Test
    public void test5() {
        String expected = "{\"Children\": [{\"RawText\": \"Hello World!\", \"Bold\": true, \"Italic\": null, \"Monospace\": null, \"Underline\": null}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}";
        String tagText = "<bold><text></bold>";
        assertMessage(expected, MessageTags.parse(tagText, TagVariable.of("text", "Hello World!")));
    }

    @Test
    public void test6() {
        String expected = "{\"Children\": [{\"RawText\": \"Hello World!\", \"Bold\": true, \"Italic\": true, \"Monospace\": null, \"Underline\": null, \"Color\": \"#ff5555\"}], \"Bold\": null, \"Italic\": null, \"Monospace\": null, \"Underline\": null}";
        String tagText = "<bold><style><text></bold>";
        assertMessage(expected, MessageTags.parse(tagText,
            TagVariable.of("text", "Hello World!"),
            TagVariable.of("style", "<red><italic>")
        ));
    }

}
