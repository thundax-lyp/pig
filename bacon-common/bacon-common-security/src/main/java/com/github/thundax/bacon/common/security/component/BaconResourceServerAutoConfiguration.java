
package com.github.thundax.bacon.common.security.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AnnotationTemplateExpressionDefaults;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

/**
 * 资源服务器自动配置类
 *
 */
@RequiredArgsConstructor
@EnableConfigurationProperties(PermitAllUrlProperties.class)
public class BaconResourceServerAutoConfiguration {

	/**
	 * 认证与授权的具体实现逻辑
	 * @return （#pms.xxx）
	 */
	@Bean("pms")
	public PermissionService permissionService() {
		return new PermissionService();
	}

	/**
	 * 访问令牌提取逻辑
	 * @param urlProperties 对外暴露的接口列表
	 * @return BearerTokenExtractor
	 */
	@Bean
	public BaconBearerTokenExtractor pigBearerTokenExtractor(PermitAllUrlProperties urlProperties) {
		return new BaconBearerTokenExtractor(urlProperties);
	}

	/**
	 * 资源服务器异常处理
	 * @param objectMapper jackson 输出对象
	 * @param securityMessageSource 自定义国际化处理器
	 * @return ResourceAuthExceptionEntryPoint
	 */
	@Bean
	public ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint(ObjectMapper objectMapper,
			MessageSource securityMessageSource) {
		return new ResourceAuthExceptionEntryPoint(objectMapper, securityMessageSource);
	}

	/**
	 * 资源服务器令牌内省处理器
	 * @param authorizationService 令牌存储实现
	 * @return 令牌内省器
	 */
	@Bean
	public OpaqueTokenIntrospector opaqueTokenIntrospector(OAuth2AuthorizationService authorizationService) {
		return new BaconCustomOpaqueTokenIntrospector(authorizationService);
	}

	/**
	 * 支持自定义权限表达式
	 * @return {@link PrePostTemplateDefaults }
	 */
	@Bean
	AnnotationTemplateExpressionDefaults prePostTemplateDefaults() {
		return new AnnotationTemplateExpressionDefaults();
	}

}
