package com.future.study.java.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author Dexterleslie.Chan
 */
public class TestMain {
    /**
     *
     * @param args
     */
    public static void main(String args[]) throws NoSuchFieldException {
        if(TestClass.class.isAnnotationPresent(Annotation1.class)){
            Annotation1 annotation1=TestClass.class.getAnnotation(Annotation1.class);
            System.out.println("annotation1="+annotation1.value());
        }

        Field field=TestClass.class.getDeclaredField("field1");
        if(field.isAnnotationPresent(Annotation2.class)){
            Annotation2 annotation2=field.getAnnotation(Annotation2.class);
            System.out.println("annotation2="+annotation2.value());
        }
    }
}
