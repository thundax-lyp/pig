
package com.github.thundax.bacon.common.feign.annotation;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 启用Pig Feign客户端注解
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableFeignClients
public @interface EnableBaconFeignClients {

	/**
	 * {@link #basePackages()}属性的别名。允许更简洁的注解声明
	 * @return 'basePackages'数组
	 */
	String[] value() default {};

	/**
	 * 扫描注解组件的基础包路径
	 * <p>
	 * 与{@link #value()}互为别名且互斥
	 * <p>
	 * 对于基于字符串的包名，可使用{@link #basePackageClasses()}作为类型安全的替代方案
	 * @return 基础包路径数组
	 */
	@AliasFor(annotation = EnableFeignClients.class, attribute = "basePackages")
	String[] basePackages() default { "com.github.thundax.bacon" };

}
