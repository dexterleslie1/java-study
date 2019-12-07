package com.future.demo.java.spring.cloud.stream.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author dexterleslie@gmail.com
 */
public interface SinkCustomize {
    /**
     *
     */
    String InputCustomize = "inputCustomize";

    /**
     *
     */
    String InputCustomizeDelay = "inputCustomizeDelay";

    /**
     *
     * @return
     */
    @Input(SinkCustomize.InputCustomize)
    SubscribableChannel inputCustomize();

    /**
     *
     * @return
     */
    @Input(SinkCustomize.InputCustomizeDelay)
    SubscribableChannel inputCustomizeDelay();
}
