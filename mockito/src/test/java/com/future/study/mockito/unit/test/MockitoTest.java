package com.future.study.mockito.unit.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * 
 * @author Dexterleslie
 * @date 2018年8月2日
 * @time 上午11:21:41
 */
public class MockitoTest {
	/**
	 * 测试mock对象是否已调用指定函数
	 */
	@Test
	public void verify_if_function_called(){
		// 创建List mock对象
		List mockListObject=Mockito.mock(List.class);
		
		mockListObject.add("val1");
		mockListObject.remove(0);
		mockListObject.clear();
		
		// 验证add("val1")、remove(0)、removeAll()是否被调用
		Mockito.verify(mockListObject).add("val1");
		Mockito.verify(mockListObject).remove(0);
		Mockito.verify(mockListObject).clear();
	}
	
	/**
	 * 模拟抛出异常
	 */
	@Test(expected=Exception.class)
	public void throw_exception(){
		List mockListObject=Mockito.mock(List.class);
		Mockito.doThrow(new Exception("模拟抛出异常")).when(mockListObject).get(1);
		
		mockListObject.get(0);
		mockListObject.get(1);
	}
	
	/**
     * Answer使用
     */
    @Test
    public void answerTest(){
    	List mockList=Mockito.mock(List.class);
    	Mockito.when(mockList.get(Mockito.anyInt())).thenAnswer(new Answer<String>(){
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return "hello world:"+args[0];
            }
        });
        Assert.assertEquals("hello world:0",mockList.get(0));
        Assert.assertEquals("hello world:999",mockList.get(999));
    }
    
    /**
     * 验证调用次数
     */
    @Test
    public void verifying_number_of_invocations(){
        List list = Mockito.mock(List.class);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(3);
        list.add(3);
        //验证是否被调用一次，等效于下面的times(1)
        Mockito.verify(list).add(1);
        Mockito.verify(list,Mockito.times(1)).add(1);
        //验证是否被调用2次
        Mockito.verify(list,Mockito.times(2)).add(2);
        //验证是否被调用3次
        Mockito.verify(list,Mockito.times(3)).add(3);
        //验证是否从未被调用过
        Mockito.verify(list,Mockito.never()).add(4);
        //验证至少调用一次
        Mockito.verify(list,Mockito.atLeastOnce()).add(1);
        //验证至少调用2次
        Mockito.verify(list,Mockito.atLeast(2)).add(2);
        //验证至多调用3次
        Mockito.verify(list,Mockito.atMost(3)).add(3);
    }
    
    /**
     * 执行顺序
     */
    @Test
    public void verification_in_order(){
        List list = Mockito.mock(List.class);
        List list2 = Mockito.mock(List.class);
        list.add(1);
        list2.add("hello");
        list.add(2);
        list2.add("world");
        //将需要排序的mock对象放入InOrder
        InOrder inOrder = Mockito.inOrder(list,list2);
        //下面的代码不能颠倒顺序，验证执行顺序
        inOrder.verify(list).add(1);
        inOrder.verify(list2).add("hello");
        inOrder.verify(list).add(2);
        inOrder.verify(list2).add("world");
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void spy_on_real_objects(){
        List list = new LinkedList();
        List spy = Mockito.spy(list);
        //下面预设的spy.get(0)会报错，因为会调用真实对象的get(0)，所以会抛出越界异常
//        Mockito.when(spy.get(0)).thenReturn(3);

        //使用doReturn-when可以避免when-thenReturn调用真实对象api
        Mockito.doReturn(999).when(spy).get(999);
        //预设size()期望值
        Mockito.when(spy.size()).thenReturn(100);
        //调用真实对象的api
        spy.add(1);
        spy.add(2);
        Assert.assertEquals(100,spy.size());
        Assert.assertEquals(1,spy.get(0));
        Assert.assertEquals(2,spy.get(1));
        Mockito.verify(spy).add(1);
        Mockito.verify(spy).add(2);
        Assert.assertEquals(999,spy.get(999));
        spy.get(2);
    }
    
    @Test
    public void real_partial_mock(){
        //通过spy来调用真实的api
        List list = Mockito.spy(new ArrayList());
        Assert.assertEquals(0,list.size());
        A a  = Mockito.mock(A.class);
        //通过thenCallRealMethod来调用真实的api
        Mockito.when(a.doSomething(Mockito.anyInt())).thenCallRealMethod();
        Assert.assertEquals(999,a.doSomething(999));
    }
    class A{
        public int doSomething(int i){
            return i;
        }
    }
}
