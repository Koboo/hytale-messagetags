package benchmarks;

import eu.koboo.messagetags.api.MessageTags;
import fi.sulku.hytale.TinyMsg;
import io.github.insideranh.talemessage.TaleMessage;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

public class SingleTagBenchmark extends AbstractBenchmark {

    @Param({
        "<bold>Hello World</bold>",
        "<color:red>Hello World</color>",
        "<red>Hello World</red>",
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