package eu.koboo.messagetags.api.tests;

import static eu.koboo.messagetags.api.tests.TestUtils.assertMessage;

import eu.koboo.messagetags.api.MessageTags;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class DirectiveTagTests {

    public record TestCase(String tagName, String expected) {
    }

    static Stream<TestCase> provide() {
        return Stream.of(
            new TestCase("lang:server.commands.help.desc", ""),
            new TestCase("reset", ""),
            new TestCase("linebreak", "")
        );
    }

    @ParameterizedTest
    @MethodSource("provide")
    public void test(TestCase testCase) {
        String tagText = "<" + testCase.tagName + "/>";
        assertMessage(testCase.expected, MessageTags.parse(tagText));
    }

}
