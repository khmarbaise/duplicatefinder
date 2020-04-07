package com.soebes.performance;

import com.soebes.performance.streams.BenchmarkStreamConcat;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BenchmarkRunner {

  public static void main(String[] args)
      throws RunnerException {

    Options opt =
        new OptionsBuilder()
            .forks(3)
            .warmupIterations(3)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)
            .include(BenchmarkStreamConcat.class.getName())
            .resultFormat(ResultFormatType.JSON)
            .build();

    new Runner(opt).run();
  }
}
