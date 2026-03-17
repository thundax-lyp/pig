package com.pig4cloud.pig.common.core.config;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * JetCache 配置
 *
 * @author lengleng
 * @date 2026/03/17
 */
@AutoConfiguration
@EnableMethodCache(basePackages = "com.pig4cloud.pig")
public class JetCacheConfiguration {

}
