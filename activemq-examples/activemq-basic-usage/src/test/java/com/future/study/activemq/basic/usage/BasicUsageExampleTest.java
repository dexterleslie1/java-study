package com.future.study.activemq.basic.usage;

import org.junit.Test;

import javax.jms.JMSException;
import java.util.Date;

/**
 * Examples for demonstrating basic usage of activemq java client
 * Reference sites:
 * https://codenotfound.com/jms-publish-subscribe-messaging-example-activemq-maven.html
 * @author Dexterleslie.Chan
 */
public class BasicUsageExampleTest {
    @Test
    public void test_publish_subscribe() throws JMSException {
        String topic = "topic-publish-subscribe";
        Publisher publisher = new Publisher("publisher-one", topic);
        publisher.connect();
        String stringTimeNow = (new Date()).toString();
        publisher.sendMessage(stringTimeNow);
        publisher.close();
    }
}
