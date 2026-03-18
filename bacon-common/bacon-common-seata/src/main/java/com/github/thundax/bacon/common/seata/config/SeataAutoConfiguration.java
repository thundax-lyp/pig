package com.github.thundax.bacon.common.seata.config;

import com.github.thundax.bacon.common.core.factory.YamlPropertySourceFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Seata 自动配置类
 *
 */
@PropertySource(value = "classpath:seata-config.yml", factory = YamlPropertySourceFactory.class)
@Configuration(proxyBeanMethods = false)
public class SeataAutoConfiguration {

}
