package com.future.study.mockito.unit.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * 
 * @author Dexterleslie
 * @date 2018年8月2日
 * @time 上午11:52:23
 */
public class AnnotationMock2Test {
	@Mock
	private List mockListObject;
	
	@Before
	public void setup(){
		// 解析@Mock注解，否则mockListObject对象为null
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * 
	 */
	@Test
	public void verify_if_function_called(){
		mockListObject.add("val1");
		Mockito.verify(mockListObject).add("val1");
	}
}
