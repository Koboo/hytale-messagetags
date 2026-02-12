package benchmarks;

import eu.koboo.messagetags.api.MessageTags;
import fi.sulku.hytale.TinyMsg;
import io.github.insideranh.talemessage.MiniMessageParser;
import io.github.insideranh.talemessage.TaleMessage;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.infra.Blackhole;

public class ComplexBenchmark extends AbstractBenchmark {

    @Param({
        "<bold>Start</bold> of <italic>a long <color:blue>nested <underline>message</underline> with multiple</color> tags</italic> to <color:green>stress-test</color> the parser.",
        "<bold>Aw<underline>eso</underline>me <color:#fcba03>ben<white>chm<red>ar<i>k</i> <color:dark_red>, but how<reset> fast <i>a</i>r<monospace>e</monospace>we really?",
        "<bold><italic><underline><color:#FF0000>Hello <color:#00FF00>world <italic>and <bold>everyone <color:#0000FF>else</color></bold></italic></color></underline></italic></bold>",
        "<color:yellow><bold>This is a very long repeated message. </bold></color><italic><color:blue>Testing multiple tags and nesting over a large amount of text. </color></italic>",
        "<color:green><underline>Here we add more depth and style to simulate a huge chat message scenario.</underline></color>",
    })
    private String input;

    @Benchmark
    public void parseMessageTags(Blackhole blackhole) {
        blackhole.consume(MessageTags.parse(input));
    }

    @Benchmark
    public void parseTinyMessages(Blackhole blackhole) {
        blackhole.consume(TinyMsg.parse(input));
    }

    @Benchmark
    public void parseTaleMessage(Blackhole blackhole) {
        blackhole.consume(TaleMessage.parse(input));
    }

    @Benchmark
    public void parseMiniMessage(Blackhole blackhole) {
        blackhole.consume(MiniMessage.miniMessage().deserialize(input));
    }
}