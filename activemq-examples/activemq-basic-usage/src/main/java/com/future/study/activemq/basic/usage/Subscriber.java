package com.future.study.activemq.basic.usage;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author Dexterleslie.Chan
 */
public class Subscriber {
    private String clientId = null;
    private String topic = null;
    private Connection connection = null;
    private MessageConsumer messageConsumer = null;

    /**
     *
     * @param clientId
     * @param topic
     */
    public Subscriber(String clientId, String topic){
        this.clientId = clientId;
        this.topic = topic;
    }

    /**
     *
     * @param timeoutInMilliseconds
     * @return
     * @throws JMSException
     */
    public String receiveMessage(int timeoutInMilliseconds) throws JMSException {
        Message message = messageConsumer.receive(timeoutInMilliseconds);
        if(message != null){
            TextMessage textMessage = (TextMessage)message;
            return textMessage.getText();
        }
        return null;
    }

    /**
     *
     * @throws JMSException
     */
    public void connect() throws JMSException {
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(Config.BrokerUrl);
        connection = connectionFactory.createConnection();
        connection.setClientID(clientId);
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(this.topic);
        messageConsumer = session.createConsumer(topic);
        // Start the connection in order to receive messages
        connection.start();
    }

    /**
     *
     * @throws JMSException
     */
    public void close() throws JMSException {
        if(this.connection != null){
            this.connection.close();
        }
    }
}
