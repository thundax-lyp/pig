package com.github.thundax.bacon.common.core.config;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * JetCache 配置
 *
 */
@AutoConfiguration
@EnableMethodCache(basePackages = "com.github.thundax.bacon")
public class JetCacheConfiguration {

}
