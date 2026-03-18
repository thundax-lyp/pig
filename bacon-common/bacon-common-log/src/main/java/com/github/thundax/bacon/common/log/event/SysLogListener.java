
package com.github.thundax.bacon.common.log.event;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.github.thundax.bacon.sys.api.dto.LogRecordDTO;
import com.github.thundax.bacon.sys.api.service.LogApi;
import com.github.thundax.bacon.common.core.jackson.BaconJavaTimeModule;
import com.github.thundax.bacon.common.log.config.BaconLogProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.Objects;

/**
 * 系统日志监听器：异步处理系统日志事件
 *
 */
@RequiredArgsConstructor
public class SysLogListener implements InitializingBean {

	// new 一个 避免日志脱敏策略影响全局ObjectMapper
	private final static ObjectMapper objectMapper = new ObjectMapper();

	private final LogApi logApi;

	private final BaconLogProperties logProperties;

	/**
	 * 异步保存系统日志
	 * @param event 系统日志事件
	 */
	@SneakyThrows
	@Async
	@Order
	@EventListener(SysLogEvent.class)
	public void saveSysLog(SysLogEvent event) {
		SysLogEventSource source = (SysLogEventSource) event.getSource();
		LogRecordDTO sysLog = new LogRecordDTO();
		BeanUtil.copyProperties(source, sysLog);

		// json 格式刷参数放在异步中处理，提升性能
		if (Objects.nonNull(source.getBody())) {
			String params = objectMapper.writeValueAsString(source.getBody());
			sysLog.setParams(StrUtil.subPre(params, logProperties.getMaxLength()));
		}

		logApi.saveLog(sysLog);
	}

	@Override
	public void afterPropertiesSet() {
		objectMapper.addMixIn(Object.class, PropertyFilterMixIn.class);
		String[] ignorableFieldNames = logProperties.getExcludeFields().toArray(new String[0]);

		FilterProvider filters = new SimpleFilterProvider().addFilter("filter properties by name",
				SimpleBeanPropertyFilter.serializeAllExcept(ignorableFieldNames));
		objectMapper.setFilterProvider(filters);
		objectMapper.registerModule(new BaconJavaTimeModule());
	}

	/**
	 * 属性过滤混合类：用于通过名称过滤属性
	 *
	 */
	@JsonFilter("filter properties by name")
	class PropertyFilterMixIn {

	}

}
