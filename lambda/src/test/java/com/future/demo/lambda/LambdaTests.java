package com.future.demo.lambda;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LambdaTests {
    @Test
    public void testAnonymousFunction() {
        List<Integer> list = new ArrayList<>();
        for(int i=0; i<100; i++) {
            list.add(i);
        }
        list = filter(list, (Integer integerObject)->{
            if(integerObject>=30) {
                return true;
            }
            return false;
        });
        Assert.assertEquals(70, list.size());
    }

    List<Integer> filter(List<Integer> list, FilterProcessor<Integer> filterProcessor) {
        List<Integer> listToReturn = new ArrayList<>();
        for(Integer integerTemporary : list) {
            if(filterProcessor.process(integerTemporary)) {
                listToReturn.add(integerTemporary);
            }
        }
        return listToReturn;
    }
}
