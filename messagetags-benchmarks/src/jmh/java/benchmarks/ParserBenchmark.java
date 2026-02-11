package benchmarks;

import eu.koboo.messagetags.api.MessageTags;
import fi.sulku.hytale.TinyMsg;
import io.github.insideranh.talemessage.TaleMessage;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 2)
@Measurement(iterations = 3, time = 3)
@Fork(3)
@State(Scope.Thread)
public class ParserBenchmark {

    @Param({
        "Hello World",
        "<bold>Hello World",
        "<color:red>Hello World",
        "<bold><italic><underline><color:red>Hello World</underline></italic></bold>",
        "Hello <bold>world</bold>, this is a <color:yellow>yellow <italic>italic</italic></color> test.",
        "<bold>Start</bold> of <italic>a long <color:blue>nested <underline>message</underline> with multiple</color> tags</italic> to <color:green>stress-test</color> the parser.",
        "<bold>Aw<underline>eso</underline>me <color:#fcba03>ben<white>chm<red>ar<i>k</i> <color:dark_red>, but how<reset> fast <i>a</i>r<monospace>e</monospace>we really?"
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
}