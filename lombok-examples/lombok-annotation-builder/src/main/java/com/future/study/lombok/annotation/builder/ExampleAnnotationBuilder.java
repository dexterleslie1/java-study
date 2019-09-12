package com.future.study.lombok.annotation.builder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dexterleslie@gmail.com
 */
@Getter
@Builder
public class ExampleAnnotationBuilder {
    private long userId;
    private ApnsType apnsType;
}
