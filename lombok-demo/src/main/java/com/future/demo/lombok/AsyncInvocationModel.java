package com.future.demo.lombok;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Getter
@SuperBuilder
public class AsyncInvocationModel {
    private AsyncInvocationType type;
    private Map<String, Object> parameters = new HashMap<>();
}
