package org.example.benchmarks.ms;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class JavaNoReflect {

    @Param({"100", "1000", "1000000"})
    int size;

    List<TestData> data;

    @Setup
    public void init() {
        data = new ArrayList<>();
        for(int i = 0; i < size; i++){
            data.add(new TestData(ThreadLocalRandom.current().nextInt(),
             String.valueOf(ThreadLocalRandom.current().nextInt())));
        }
    }

    public String serialize(TestData data){
        String result = "{" + "\"field1\":" + data.field1 + ",\"field2\":" + data.field2 + "}";
        return result;
    }

    @Benchmark
    public void benchmarkMethod(Blackhole bh) {
        for(TestData test : data){
            serialize(test);
        }
        bh.consume(data);
    }
}
