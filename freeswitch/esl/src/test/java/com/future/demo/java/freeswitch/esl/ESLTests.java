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
    	client.connect("192.168.1.184", 8021, "xxxxx", 10);
    	EslMessage message = client.sendSyncApiCommand("sofia_contact", "1000");
    	List<String> response = message.getBodyLines();
    	System.out.println(response);
    	client.close();
    }
}
