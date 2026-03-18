package com.github.thundax.bacon.common.security.service;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.template.QuickConfig;
import com.github.thundax.bacon.common.core.constant.CacheConstants;
import com.github.thundax.bacon.common.core.support.JetCacheVersionSupport;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collection;

/**
 * 用户详情缓存服务
 *
 */
@Component
@RequiredArgsConstructor
public class UserDetailsCacheService {

	private final CacheManager cacheManager;

	private final JetCacheVersionSupport jetCacheVersionSupport;

	private Cache<String, UserDetails> cache;

	@PostConstruct
	public void init() {
		QuickConfig quickConfig = QuickConfig.newBuilder(CacheConstants.USER_DETAILS + ":")
			.expire(Duration.ofMinutes(30))
			.cacheType(CacheType.REMOTE)
			.build();
		cache = cacheManager.getOrCreateCache(quickConfig);
	}

	public UserDetails get(String username) {
		return cache.get(versionedKey(username));
	}

	public void put(String username, UserDetails userDetails) {
		cache.put(versionedKey(username), userDetails);
	}

	public void remove(String username) {
		cache.remove(versionedKey(username));
	}

	public void removeAll(Collection<String> usernames) {
		usernames.stream().map(this::versionedKey).forEach(cache::remove);
	}

	public void clear() {
		jetCacheVersionSupport.increment(CacheConstants.USER_DETAILS);
	}

	private String versionedKey(String username) {
		return jetCacheVersionSupport.versionedKey(CacheConstants.USER_DETAILS, username);
	}

}
