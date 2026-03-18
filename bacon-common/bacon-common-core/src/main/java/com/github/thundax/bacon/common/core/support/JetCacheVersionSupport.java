package com.github.thundax.bacon.common.core.support;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * JetCache 版本键支持，用于替代 Spring Cache 的 allEntries 语义。
 *
 */
@Component("jetCacheVersionSupport")
@RequiredArgsConstructor
public class JetCacheVersionSupport {

	private static final String CACHE_VERSION_PREFIX = "jetcache:version:";

	private final StringRedisTemplate stringRedisTemplate;

	public String versionedKey(String cacheName, Object key) {
		String version = stringRedisTemplate.opsForValue().get(versionKey(cacheName));
		return (version == null ? "0" : version) + ":" + key;
	}

	public void increment(String cacheName) {
		stringRedisTemplate.opsForValue().increment(versionKey(cacheName));
	}

	private String versionKey(String cacheName) {
		return CACHE_VERSION_PREFIX + cacheName;
	}

}
