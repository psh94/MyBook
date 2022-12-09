package com.psh.mybook.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/*
@Target(ElementType.PARAMETER) : 파라미터에만 사용할 것
@Retention(RetentionPolicy.RUNTIME) : 런타임까지 애노테이션 정보가 남아있음
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {
}
