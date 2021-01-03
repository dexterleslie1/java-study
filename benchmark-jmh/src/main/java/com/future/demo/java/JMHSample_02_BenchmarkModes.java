package com.future.demo.java;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 *
 */
public class JMHSample_02_BenchmarkModes {
    /**
     * 每秒多少次op
     * @throws InterruptedException
     */
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void measureThroughput() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    /**
     * 每次op耗时多少
     * @throws InterruptedException
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void measureAvgTime() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    /**
     * 每次op耗时样本百分比分布
     * @throws InterruptedException
     */
    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void measureSamples() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    /**
     * 只调用一次op耗时多少
     * @throws InterruptedException
     */
    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void measureSingleShot() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    /**
     *
     * @throws InterruptedException
     */
    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime})
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void measureMultiple() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    /**
     *
     * @throws InterruptedException
     */
    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void measureAll() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
    }

    /**
     *
     * @param args
     * @throws RunnerException
     */
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHSample_02_BenchmarkModes.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
