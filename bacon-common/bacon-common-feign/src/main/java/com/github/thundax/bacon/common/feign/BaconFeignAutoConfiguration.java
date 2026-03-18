
package com.github.thundax.bacon.common.feign;

import com.alibaba.cloud.sentinel.feign.SentinelFeignAutoConfiguration;
import com.github.thundax.bacon.common.feign.core.BaconFeignInnerRequestInterceptor;
import com.github.thundax.bacon.common.feign.core.BaconFeignRequestCloseInterceptor;
import com.github.thundax.bacon.common.feign.sentinel.ext.BaconSentinelFeign;
import feign.Feign;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.BaconFeignClientsRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

/**
 * Sentinel Feign 自动配置类
 *
 */
@Configuration(proxyBeanMethods = false)
@Import(BaconFeignClientsRegistrar.class)
@AutoConfigureBefore(SentinelFeignAutoConfiguration.class)
public class BaconFeignAutoConfiguration {

	/**
	 * 创建Feign.Builder实例，支持Sentinel功能
	 * @return Feign.Builder实例
	 * @ConditionalOnMissingBean 当容器中不存在该类型bean时创建
	 * @ConditionalOnProperty 当配置feign.sentinel.enabled为true时生效
	 * @Scope 指定bean作用域为prototype
	 */
	@Bean
	@Scope("prototype")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(name = "feign.sentinel.enabled")
	public Feign.Builder feignSentinelBuilder() {
		return BaconSentinelFeign.builder();
	}

	/**
	 * 创建并返回BaconFeignRequestCloseInterceptor实例
	 * @return BaconFeignRequestCloseInterceptor实例
	 */
	@Bean
	public BaconFeignRequestCloseInterceptor pigFeignRequestCloseInterceptor() {
		return new BaconFeignRequestCloseInterceptor();
	}

	/**
	 * 创建并返回BaconFeignInnerRequestInterceptor实例
	 * @return BaconFeignInnerRequestInterceptor 内部请求拦截器实例
	 */
	@Bean
	public BaconFeignInnerRequestInterceptor pigFeignInnerRequestInterceptor() {
		return new BaconFeignInnerRequestInterceptor();
	}

}
