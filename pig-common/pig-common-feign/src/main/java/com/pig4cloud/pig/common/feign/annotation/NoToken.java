package com.pig4cloud.pig.common.feign.annotation;

import java.lang.annotation.*;

/**
 * 服务无令牌调用声明注解
 * <p>
 * 只有调用方没有令牌时才需要添加此注解，通常与 `@Inner` 配合使用
 * <p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoToken {

}
