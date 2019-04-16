package com.future.study.activemq.basic.usage;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author Dexterleslie.Chan
 */
public class Publisher {
    private String clientId         = null;
    private String topic            = null;
    private Connection connection   = null;
    private Session session         = null;
    private MessageProducer messageProducer = null;

    /**
     *
     * @param clientId
     * @param topic
     */
    public Publisher(String clientId, String topic){
        this.clientId = clientId;
        this.topic = topic;
    }

    /**
     * Send text message
     * @param textMessage
     * @throws JMSException
     */
    public void sendMessage(String textMessage) throws JMSException {
        TextMessage message = session.createTextMessage(textMessage);
        messageProducer.send(message);
    }

    /**
     * Connect to activemq
     * @throws JMSException
     */
    public void connect() throws JMSException {
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(Config.BrokerUrl);
        connection = connectionFactory.createConnection();
        connection.setClientID(clientId);
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(this.topic);
        messageProducer = session.createProducer(topic);
    }

    /**
     * Close connection
     * @throws JMSException
     */
    public void close() throws JMSException {
        if(connection != null){
            connection.close();
        }
    }
}
