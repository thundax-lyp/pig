package com.pig4cloud.pig.sys.api.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.pig4cloud.pig.sys.api.service.ParamApi;
import com.pig4cloud.pig.common.core.util.SpringContextHolder;
import lombok.experimental.UtilityClass;

/**
 * 系统参数配置解析器工具类
 *
 * @author lengleng
 * @date 2025/05/30
 */
@UtilityClass
public class ParamResolver {

	/**
	 * 根据键获取 Long 类型参数配置
	 * @param key 参数键
	 * @param defaultVal 默认值
	 * @return 参数值
	 */
	public Long getLong(String key, Long... defaultVal) {
		return checkAndGet(key, Long.class, defaultVal);
	}

	/**
	 * 根据键获取 String 类型参数配置
	 * @param key 参数键
	 * @param defaultVal 默认值
	 * @return 参数值
	 */
	public String getStr(String key, String... defaultVal) {
		return checkAndGet(key, String.class, defaultVal);
	}

	/**
	 * 根据键获取远程参数值并转换为指定类型
	 * @param key 参数键
	 * @param clazz 目标类型
	 * @param defaultVal 默认值（可选，最多一个）
	 * @param <T> 泛型类型
	 * @return 转换后的参数值，未找到且无默认值时返回null
	 * @throws IllegalArgumentException 参数不合法时抛出异常
	 */
	private <T> T checkAndGet(String key, Class<T> clazz, T... defaultVal) {
		// 校验入参是否合法
		if (StrUtil.isBlank(key) || defaultVal.length > 1) {
			throw new IllegalArgumentException("参数不合法");
		}

		ParamApi paramApi = SpringContextHolder.getBean(ParamApi.class);

		String result = paramApi.getByKey(key).getData();

		if (StrUtil.isNotBlank(result)) {
			return Convert.convert(clazz, result);
		}

		if (defaultVal.length == 1) {
			return Convert.convert(clazz, defaultVal.clone()[0]);

		}
		return null;
	}

}
