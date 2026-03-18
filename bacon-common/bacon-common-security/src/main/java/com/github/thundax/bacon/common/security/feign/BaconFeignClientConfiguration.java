
package com.github.thundax.bacon.common.security.feign;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;

/**
 * Pig Feign 客户端配置类
 *
 */
public class BaconFeignClientConfiguration {

	/**
	 * 注入 oauth2 feign token 增强
	 * @param tokenResolver token获取处理器
	 * @return 拦截器
	 */
	@Bean
	public RequestInterceptor oauthRequestInterceptor(BearerTokenResolver tokenResolver) {
		return new BaconOAuthRequestInterceptor(tokenResolver);
	}

}
