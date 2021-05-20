package com.future.demo.lambda;

@FunctionalInterface
public interface FilterProcessor<T> {
    boolean process(T t);
}
