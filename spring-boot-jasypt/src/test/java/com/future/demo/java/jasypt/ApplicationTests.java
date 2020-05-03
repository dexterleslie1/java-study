package com.future.demo.java.jasypt;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * vm arguments或者environment variable添加jasypt.encryptor.password=******
 * @author dexterleslie@gmail.com
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class ApplicationTests {
	@Value("${com.future.demo.java.property1}")
	private String property1 = null;
	@Value("${com.future.demo.java.property2}")
	private String property2 = null;
	
	/**
	 * 
	 */
	@Test
	public void test1() {
		Assert.assertEquals("secrettext", property1);
		Assert.assertEquals("plaintext", property2);
	}
}
