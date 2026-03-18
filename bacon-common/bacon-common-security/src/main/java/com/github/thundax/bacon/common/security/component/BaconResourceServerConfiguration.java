
package com.github.thundax.bacon.common.security.component;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * 资源服务器认证与授权配置
 *
 */
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class BaconResourceServerConfiguration {

	/**
	 * 资源认证异常处理入口点
	 */
	protected final ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint;

	/**
	 * 允许所有URL的配置属性
	 */
	private final PermitAllUrlProperties permitAllUrl;

	/**
	 * PigBearerToken提取器
	 */
	private final BaconBearerTokenExtractor pigBearerTokenExtractor;

	/**
	 * 自定义不透明令牌内省器
	 */
	private final OpaqueTokenIntrospector customOpaqueTokenIntrospector;

	/**
	 * CORS跨域资源共享配置属性
	 */
	private final BaconBootCorsProperties pigBootCorsProperties;

	/**
	 * 资源服务器安全配置
	 * @param http http
	 * @return {@link SecurityFilterChain }
	 * @throws Exception 异常
	 */
	@Bean
	SecurityFilterChain resourceServer(HttpSecurity http) throws Exception {
		/**
		 * AntPathRequestMatcher[] permitMatchers = permitAllUrl.getUrls() .stream()
		 * .map(AntPathRequestMatcher::new) .toList() .toArray(new AntPathRequestMatcher[]
		 * {});
		 **/
		PathPatternRequestMatcher[] permitMatchers = permitAllUrl.getUrls()
			.stream()
			.map(url -> PathPatternRequestMatcher.withDefaults().matcher(url))
			.toList()
			.toArray(new PathPatternRequestMatcher[] {});

		http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers(permitMatchers)
			.permitAll()
			.anyRequest()
			.authenticated())
			.oauth2ResourceServer(
					oauth2 -> oauth2.opaqueToken(token -> token.introspector(customOpaqueTokenIntrospector))
						.authenticationEntryPoint(resourceAuthExceptionEntryPoint)
						.bearerTokenResolver(pigBearerTokenExtractor))
			.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
			.csrf(AbstractHttpConfigurer::disable);

		// 配置 CORS 跨域资源共享
		if (Boolean.TRUE.equals(pigBootCorsProperties.getEnabled())) {
			http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
		}

		return http.build();
	}

	/**
	 * 配置 CORS 跨域资源共享
	 * @return UrlBasedCorsConfigurationSource CORS配置源
	 */
	private UrlBasedCorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();

		// 从配置文件读取允许的源模式
		pigBootCorsProperties.getAllowedOriginPatterns().forEach(corsConfiguration::addAllowedOriginPattern);
		// 从配置文件读取允许的请求头
		pigBootCorsProperties.getAllowedHeaders().forEach(corsConfiguration::addAllowedHeader);
		// 从配置文件读取允许的HTTP方法
		pigBootCorsProperties.getAllowedMethods().forEach(corsConfiguration::addAllowedMethod);
		// 从配置文件读取是否允许携带凭证
		corsConfiguration.setAllowCredentials(pigBootCorsProperties.getAllowCredentials());

		// 注册CORS配置到指定路径
		source.registerCorsConfiguration(pigBootCorsProperties.getPathPattern(), corsConfiguration);

		return source;
	}

}
