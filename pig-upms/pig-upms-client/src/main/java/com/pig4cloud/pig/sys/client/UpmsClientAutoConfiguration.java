package com.pig4cloud.pig.sys.client;

import com.pig4cloud.pig.sys.api.feign.*;
import com.pig4cloud.pig.sys.api.service.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * UPMS 客户端自动配置
 *
 * @author lengleng
 * @date 2026/03/16
 */
@Configuration(proxyBeanMethods = false)
public class UpmsClientAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public UserQueryApi userQueryApi(RemoteUserService remoteUserService) {
		return remoteUserService::info;
	}

	@Bean
	@ConditionalOnMissingBean
	public ClientDetailsApi clientDetailsApi(RemoteClientDetailsService remoteClientDetailsService) {
		return remoteClientDetailsService::getClientDetailsById;
	}

	@Bean
	@ConditionalOnMissingBean
	public DictApi dictApi(RemoteDictService remoteDictService) {
		return remoteDictService::getDictByType;
	}

	@Bean
	@ConditionalOnMissingBean
	public ParamApi paramApi(RemoteParamService remoteParamService) {
		return remoteParamService::getByKey;
	}

	@Bean
	@ConditionalOnMissingBean
	public LogApi logApi(RemoteLogService remoteLogService) {
		return remoteLogService::saveLog;
	}

}
