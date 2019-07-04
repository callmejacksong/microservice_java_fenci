package com.melink.open.api.annotation.mybatis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyResult {
  String foreign() default "";// 外键key的字段名称
  String column() default "";// bean中与外键对应字段名称
  String property() default "";// bean中接收外键对象的字段名称
  //外键表对应的mapper要提供一个通过外键字段的list获取外键对象的mapper方法
  String one() default "";// 一对一时，提供外键mapper方法的路径
  String many() default "";// 一对多时，提供外键mapper方法的路径
}
