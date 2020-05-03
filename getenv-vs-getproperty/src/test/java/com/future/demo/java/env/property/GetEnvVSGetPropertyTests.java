package com.future.demo.java.env.property;

import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class GetEnvVSGetPropertyTests {
	/**
	 * 
	 */
	@Test
	public void displayCurrentEnvVars() {
		Map<String, String> environmentVariables = System.getenv();
		for(String keyTemporary : environmentVariables.keySet()) {
			System.out.println(keyTemporary + "=" + environmentVariables.get(keyTemporary));
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void getEnv() {
		String envKey = "welcome";
		String envVal = System.getenv(envKey);
		Assert.assertEquals("Hi Dexter", envVal);
	}
	
	/**
	 * 
	 */
	@Test
	public void getProperties() {
		Properties properties = System.getProperties();
		for(Object keyTemporary : properties.keySet()) {
			Object valTemporary = properties.get(keyTemporary);
			System.out.println(keyTemporary + "=" + valTemporary);
		}
	}
	
	/**
	 * vm arguments先设置-Dproperty1=value1
	 */
	@Test
	public void getProperty() {
		String valueTemporary = System.getProperty("property1");
		Assert.assertEquals("value1", valueTemporary);
	}
}
