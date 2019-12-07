package com.future.demo.java.spring.cloud.stream.producer;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author dexterleslie@gmail.com
 */
public interface SourceCustomize {
    /**
     *
     * @return
     */
    @Output("outputCustomize")
    MessageChannel outputCustomize();

    /**
     *
     * @return
     */
    @Output("outputCustomizeDelay")
    MessageChannel outputCustomizeDelay();
}
