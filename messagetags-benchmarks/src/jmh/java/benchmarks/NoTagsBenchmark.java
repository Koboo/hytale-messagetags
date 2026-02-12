package benchmarks;

import eu.koboo.messagetags.api.MessageTags;
import fi.sulku.hytale.TinyMsg;
import io.github.insideranh.talemessage.TaleMessage;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.infra.Blackhole;

public class NoTagsBenchmark extends AbstractBenchmark {

    @Param({
        "Hello World",
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