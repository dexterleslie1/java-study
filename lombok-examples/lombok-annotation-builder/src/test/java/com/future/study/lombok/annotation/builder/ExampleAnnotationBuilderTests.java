package com.future.study.lombok.annotation.builder;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class ExampleAnnotationBuilderTests {
    @Test
    public void test() {
        ExampleAnnotationBuilder instance = ExampleAnnotationBuilder.builder()
                .userId(1)
                .apnsType(ApnsType.Application)
                .build();
        Assert.assertEquals(ApnsType.Application, instance.getApnsType());
        Assert.assertEquals(1, instance.getUserId());
    }
}
