package benchmarks;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode({
    Mode.SingleShotTime
})
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 2)
@Measurement(iterations = 5, time = 3)
@Fork(5)
@State(Scope.Thread)
@Threads(3)
public abstract class AbstractBenchmark {

}
