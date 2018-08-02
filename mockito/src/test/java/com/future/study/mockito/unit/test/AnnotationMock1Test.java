package com.future.study.mockito.unit.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * 
 * @author Dexterleslie
 * @date 2018年8月2日
 * @time 上午11:52:18
 */
@RunWith(MockitoJUnitRunner.class)
public class AnnotationMock1Test {
	@Mock
	private List mockListObject;
	
	/**
	 * 
	 */
	@Test
	public void verify_if_function_called(){
		mockListObject.add("val1");
		Mockito.verify(mockListObject).add("val1");
	}
}
