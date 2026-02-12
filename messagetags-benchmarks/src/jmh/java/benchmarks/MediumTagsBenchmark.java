package benchmarks;

import eu.koboo.messagetags.api.MessageTags;
import fi.sulku.hytale.TinyMsg;
import io.github.insideranh.talemessage.TaleMessage;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;


public class MediumTagsBenchmark extends AbstractBenchmark {

    @Param({
        "<bold><italic><underline><color:red>Hello World</underline></italic></bold>",
        "Hello <bold>world</bold>, this is a <color:yellow>yellow <italic>italic</italic></color> test.",
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