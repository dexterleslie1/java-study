package com.future.study.java.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Dexterleslie.Chan
 */
@Retention(value= RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface Annotation1 {
    int value() default 1;
}
