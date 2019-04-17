package com.future.study.activemq.basic.usage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.jms.JMSException;
import java.util.Date;

/**
 * Examples for demonstrating multiple subscriber
 * Reference sites:
 * https://codenotfound.com/jms-publish-subscribe-messaging-example-activemq-maven.html
 * @author Dexterleslie.Chan
 */
public class MultipleSubscriberExampleTest {
    @Test
    public void test_publish_multiple_subscribe() throws JMSException {
        String stringTimeNow = (new Date()).toString();
        publisher.sendMessage(stringTimeNow);

        String message = subscriberOne.receiveMessage(1000);
        Assert.assertEquals(stringTimeNow, message);

        message = subscriberTwo.receiveMessage(1000);
        Assert.assertEquals(stringTimeNow, message);
    }

    private Publisher publisher = null;
    private Subscriber subscriberOne = null;
    private Subscriber subscriberTwo = null;

    @Before
    public void setup() throws JMSException {
        String topic = "topic-publish-multiple-subscribe";
        publisher = new Publisher("publisher-one", topic);
        publisher.connect();

        subscriberOne = new Subscriber("subscriber-one", topic);
        subscriberOne.connect();

        subscriberTwo = new Subscriber("subscriber-two", topic);
        subscriberTwo.connect();
    }

    @After
    public void teardown() throws JMSException {
        publisher.close();
        subscriberOne.close();
        subscriberTwo.close();
    }
}
