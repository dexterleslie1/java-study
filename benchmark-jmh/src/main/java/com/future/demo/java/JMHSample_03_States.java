package com.future.demo.java;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 *
 */
public class JMHSample_03_States {
    /**
     * 共享一个对象实例
     */
    @State(Scope.Benchmark)
    public static class BenchmarkState {
        volatile double x = Math.PI;
    }

    /**
     * 相当于ThreadLocal
     */
    @State(Scope.Thread)
    public static class ThreadState {
        volatile double x = Math.PI;
    }

    /**
     *
     * @param state
     */
    @Benchmark
    public void measureUnshared(ThreadState state) {
        state.x++;
    }

    /**
     *
     * @param state
     */
    @Benchmark
    public void measureShared(BenchmarkState state) {
        state.x++;
    }

    /**
     *
     * @param args
     * @throws RunnerException
     */
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHSample_03_States.class.getSimpleName())
                .threads(4)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
