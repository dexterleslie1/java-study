package com.future.demo.java.freeswitch.esl;

import java.util.List;

import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.inbound.InboundConnectionFailure;
import org.freeswitch.esl.client.transport.message.EslMessage;
import org.junit.Test;

/**
 *
 */
public class ESLTests {
	/**
	 * 
	 * @throws InboundConnectionFailure
	 */
    @Test
    public void test() throws InboundConnectionFailure {
    	Client client = new Client();
    	client.connect("23.91.96.192", 8021, "1qaz2wsxAa321321", 10);
    	EslMessage message = client.sendSyncApiCommand("sofia_contact", "1000");
    	List<String> response = message.getBodyLines();
    	System.out.println(response);
    	client.close();
    }
}
