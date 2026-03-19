package com.github.thundax.bacon.common.core.support;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.template.QuickConfig;
import com.github.thundax.bacon.common.core.constant.CacheConstants;
import com.github.thundax.bacon.common.core.constant.SecurityConstants;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 验证码缓存服务
 */
@Component
@RequiredArgsConstructor
public class DefaultCodeCacheService {

	private final CacheManager cacheManager;

	private Cache<String, String> cache;

	@PostConstruct
	public void init() {
		QuickConfig quickConfig = QuickConfig.newBuilder(CacheConstants.DEFAULT_CODE_CACHE + ":")
				.expire(Duration.ofSeconds(SecurityConstants.CODE_TIME))
				.cacheType(CacheType.REMOTE)
				.build();
		cache = cacheManager.getOrCreateCache(quickConfig);
	}

	public String get(String key) {
		return cache.get(key);
	}

	public void put(String key, String value) {
		cache.put(key, value);
	}

	public void remove(String key) {
		cache.remove(key);
	}

}
