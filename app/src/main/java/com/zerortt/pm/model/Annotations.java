package com.zerortt.pm.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Key{}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Index{
    boolean unique() default true;
    boolean cacheable() default true;
}
