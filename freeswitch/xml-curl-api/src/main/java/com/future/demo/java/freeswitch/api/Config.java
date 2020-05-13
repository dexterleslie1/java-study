package com.future.demo.java.freeswitch.api;

import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.inbound.InboundConnectionFailure;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author dexterleslie@gmail.com
 *
 */
@Configuration
public class Config {
	private String eslHost = "192.168.3.28";
	private int eslPort = 8021;
	private String eslPassword = "123456";
	
	/**
	 * 
	 * @return
	 * @throws InboundConnectionFailure
	 */
	@Bean(destroyMethod="close")
	public Client client() throws InboundConnectionFailure {
		Client client = new Client();
    	client.connect(eslHost, eslPort, eslPassword, 10);
    	return client;
	}
}
