package com.github.thundax.bacon.common.xss.core;

import com.github.thundax.bacon.common.core.util.SpringContextHolder;
import com.github.thundax.bacon.common.xss.config.BaconXssProperties;
import com.github.thundax.bacon.common.xss.utils.XssUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Jackson XSS 处理反序列化器
 *
 */
@Slf4j
public class XssCleanDeserializer extends XssCleanDeserializerBase {

	/**
	 * 清理文本中的XSS内容
	 * @param name 名称
	 * @param text 待清理的文本
	 * @return 清理后的文本
	 * @throws IOException IO异常
	 */
	@Override
	public String clean(String name, String text) throws IOException {
		// 读取 xss 配置
		BaconXssProperties properties = SpringContextHolder.getBean(BaconXssProperties.class);
		// 读取 XssCleaner bean
		XssCleaner xssCleaner = SpringContextHolder.getBean(XssCleaner.class);
		if (xssCleaner != null) {
			String value = xssCleaner.clean(XssUtil.trim(text, properties.isTrimText()));
			log.debug("Json property value:{} cleaned up by mica-xss, current value is:{}.", text, value);
			return value;
		}
		else {
			return XssUtil.trim(text, properties.isTrimText());
		}
	}

}
