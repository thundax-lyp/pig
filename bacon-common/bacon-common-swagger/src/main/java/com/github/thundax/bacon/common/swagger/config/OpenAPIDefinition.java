package com.github.thundax.bacon.common.swagger.config;

import com.github.thundax.bacon.common.swagger.support.SwaggerProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.NonNull;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger配置类，用于配置OpenAPI定义
 * <p>
 * 支持通过配置控制Swagger的启用状态，并提供OAuth2安全认证配置
 * </p>
 *
 */
@RequiredArgsConstructor
@ConditionalOnProperty(name = "swagger.enabled", matchIfMissing = true)
public class OpenAPIDefinition extends OpenAPI implements InitializingBean, ApplicationContextAware {

	@Setter
	private String path;

	/**
	 * 应用上下文
	 */
	private ApplicationContext applicationContext;

	/**
	 * 创建并配置OAuth2安全方案
	 * @param swaggerProperties Swagger配置属性
	 * @return 配置好的SecurityScheme对象
	 */
	private SecurityScheme securityScheme(SwaggerProperties swaggerProperties) {
		OAuthFlow clientCredential = new OAuthFlow();
		clientCredential.setTokenUrl(swaggerProperties.getTokenUrl());
		clientCredential.setScopes(new Scopes().addString(swaggerProperties.getScope(), swaggerProperties.getScope()));
		OAuthFlows oauthFlows = new OAuthFlows();
		oauthFlows.password(clientCredential);
		SecurityScheme securityScheme = new SecurityScheme();
		securityScheme.setType(SecurityScheme.Type.OAUTH2);
		securityScheme.setFlows(oauthFlows);
		return securityScheme;
	}

	/**
	 * 初始化Swagger配置
	 */
	@Override
	public void afterPropertiesSet() {
		SwaggerProperties swaggerProperties = applicationContext.getBean(SwaggerProperties.class);
		this.info(new Info().title(swaggerProperties.getTitle()));
		// oauth2.0 password
		this.schemaRequirement(HttpHeaders.AUTHORIZATION, this.securityScheme(swaggerProperties));
		// servers
		List<Server> serverList = new ArrayList<>();
		serverList.add(new Server().url(swaggerProperties.getGateway() + "/" + path));
		this.servers(serverList);
		// 支持参数平铺
		SpringDocUtils.getConfig().addSimpleTypesForParameterObject(Class.class);
	}

	/**
	 * 设置应用上下文
	 * @param applicationContext 应用上下文对象
	 * @throws BeansException 如果设置上下文时发生错误
	 */
	@Override
	public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
