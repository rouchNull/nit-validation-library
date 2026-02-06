package co.com.dian.nit.core.benchmark;

import co.com.dian.nit.core.contract.NitValidator;
import co.com.dian.nit.core.validation.DianNitValidator;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Fork(1)
@Warmup(iterations = 2)
@Measurement(iterations = 3)
public class NitValidationBenchmark {

    private final NitValidator validator = new DianNitValidator();
    private final String validNit = "860002964-4";

    @Benchmark
    public boolean validateSingleNit() {
        return validator.isValid(validNit);
    }

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(NitValidationBenchmark.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }
}
