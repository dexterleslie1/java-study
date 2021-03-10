package com.future.demo.spring.cloud.hystrix;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApiFriendFallbackFactory implements FallbackFactory<ApiFriend> {
    @Override
    public ApiFriend create(Throwable throwable) {
        log.error(throwable.getMessage(), throwable);
        return new ApiFriend.ApiFriendFallback();
    }
}
