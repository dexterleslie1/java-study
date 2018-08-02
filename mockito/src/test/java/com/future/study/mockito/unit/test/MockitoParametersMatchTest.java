package com.future.study.mockito.unit.test;

import java.util.Comparator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

/**
 * 参数匹配测试
 * @author Dexterleslie
 * @date 2018年8月2日
 * @time 上午11:52:27
 */
public class MockitoParametersMatchTest {
	/**
	 * 匹配指定参数
	 */
	@Test
	public void specific_parameters_match(){
		List mockListObject=Mockito.mock(List.class);
		Mockito.when(mockListObject.add("val1")).thenReturn(false);
		Mockito.when(mockListObject.add("val2")).thenReturn(true);
		Assert.assertFalse(mockListObject.add("val1"));
		Assert.assertTrue(mockListObject.add("val2"));
	}
	
	/**
	 * 任意参数值匹配
	 */
	@Test
	public void any_parameters_value_match(){
		List mockListObject=Mockito.mock(List.class);
		Mockito.when(mockListObject.add(Mockito.anyInt())).thenReturn(true);
		for(int i=0;i<1000;i++){
			Assert.assertTrue(mockListObject.add(i));
		}
	}
	
	/**
	 * 使用ArgumentMatcher匹配指定参数
	 */
	@Test
	public void check_parameters_with_argument_matcher(){
		List mockListObject=Mockito.mock(List.class);
		Mockito.when(mockListObject.contains(Mockito.argThat(new IsValid()))).thenReturn(true);
		Assert.assertFalse(mockListObject.contains(3));
		Assert.assertTrue(mockListObject.contains(2));
	}
	private class IsValid extends ArgumentMatcher<Integer>{
        @Override
        public boolean matches(Object o) {
            return (Integer)o == 1 || (Integer)o == 2;
        }
    }
	
	/**
	 * 调用verify验证时匹配调用参数
	 */
	@Test
	public void verify_parameters_match(){
		Comparator comparator = Mockito.mock(Comparator.class);
        comparator.compare("nihao","hello");
        //如果你使用了参数匹配，那么所有的参数都必须通过matchers来匹配
        Mockito.verify(comparator).compare(Mockito.anyString(),Mockito.eq("hello"));
	}
	
	/**
	 * ArgumentCaptor使用
	 */
	@Test
	public void argument_captor(){
		PersonDao personDao = Mockito.mock(PersonDao.class);
        PersonService personService = new PersonService(personDao);

        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        personService.update(1,"jack");
        Mockito.verify(personDao).update(argument.capture());
        Assert.assertEquals(1,argument.getValue().getId());
        Assert.assertEquals("jack",argument.getValue().getName());
	}
	class Person{
        private int id;
        private String name;

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    interface PersonDao{
        public void update(Person person);
    }

    class PersonService{
        private PersonDao personDao;

        PersonService(PersonDao personDao) {
            this.personDao = personDao;
        }

        public void update(int id,String name){
            personDao.update(new Person(id,name));
        }
    }
}
