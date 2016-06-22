package org.example.benchmarks.ms

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
open public class KotlinNoReflect {

    @Param("100", "1000", "1000000")
    var size: Int = 0

    var data:ArrayList<TestData> = ArrayList()

    @Setup
    public fun init() {
        data = ArrayList()
        for (i in 0..size - 1) {
            data.add(TestData(ThreadLocalRandom.current().nextInt(),
                     "${ThreadLocalRandom.current().nextInt()}"))
            }
    }

    private fun serialize(data:TestData):String {
        val result = "{ \"field1\":${data.field1},\"field2\":${data.field2}}"
        return result
    }

    @Benchmark
    public fun benchmarkMethod(bh: Blackhole) {
        for (test in data) {
          serialize(test)
        }
        bh.consume(data)
    }
}
