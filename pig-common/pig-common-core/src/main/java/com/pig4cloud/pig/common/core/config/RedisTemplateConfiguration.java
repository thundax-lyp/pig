/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pig4cloud.pig.common.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.pig4cloud.pig.common.core.constant.CacheConstants;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Redis 配置类
 *
 * @author lengleng
 * @date 2025/05/30
 */
@EnableCaching
@AutoConfiguration
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisTemplateConfiguration {

	/**
	 * 创建并配置RedisTemplate实例
	 * @param factory Redis连接工厂
	 * @param objectMapper Jackson 对象映射器
	 * @return 配置好的RedisTemplate实例
	 */
	@Bean
	@Primary
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory, ObjectMapper objectMapper) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		RedisSerializer<Object> jsonSerializer = redisSerializer(objectMapper);
		redisTemplate.setKeySerializer(RedisSerializer.string());
		redisTemplate.setHashKeySerializer(RedisSerializer.string());
		redisTemplate.setValueSerializer(jsonSerializer);
		redisTemplate.setHashValueSerializer(jsonSerializer);
		redisTemplate.setConnectionFactory(factory);
		return redisTemplate;
	}

	/**
	 * 自定义Spring Cache的Redis配置，统一使用Jackson序列化并按缓存名设置TTL
	 * @param objectMapper Jackson 对象映射器
	 * @return RedisCacheManager 构建器自定义器
	 */
	@Bean
	public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(ObjectMapper objectMapper) {
		RedisSerializer<Object> jsonSerializer = redisSerializer(objectMapper);
		RedisSerializationContext.SerializationPair<Object> serializationPair = RedisSerializationContext.SerializationPair
			.fromSerializer(jsonSerializer);

		RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
			.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
			.serializeValuesWith(serializationPair)
			.disableCachingNullValues()
			.entryTtl(Duration.ofMinutes(30));

		Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
		cacheConfigurations.put(CacheConstants.DEFAULT_CODE_CACHE,
				defaultCacheConfiguration.entryTtl(Duration.ofSeconds(60)));
		cacheConfigurations.put(CacheConstants.USER_DETAILS, defaultCacheConfiguration.entryTtl(Duration.ofMinutes(30)));
		cacheConfigurations.put(CacheConstants.MENU_DETAILS, defaultCacheConfiguration.entryTtl(Duration.ofMinutes(30)));
		cacheConfigurations.put(CacheConstants.ROLE_DETAILS, defaultCacheConfiguration.entryTtl(Duration.ofMinutes(30)));
		cacheConfigurations.put(CacheConstants.DICT_DETAILS, defaultCacheConfiguration.entryTtl(Duration.ofHours(1)));
		cacheConfigurations.put(CacheConstants.PARAMS_DETAILS, defaultCacheConfiguration.entryTtl(Duration.ofHours(1)));
		cacheConfigurations.put(CacheConstants.CLIENT_DETAILS_KEY,
				defaultCacheConfiguration.entryTtl(Duration.ofHours(12)));

		return builder -> builder.cacheDefaults(defaultCacheConfiguration)
			.withInitialCacheConfigurations(cacheConfigurations);
	}

	private RedisSerializer<Object> redisSerializer(ObjectMapper objectMapper) {
		ObjectMapper redisObjectMapper = objectMapper.copy();
		redisObjectMapper.activateDefaultTyping(
				BasicPolymorphicTypeValidator.builder().allowIfSubType(Object.class).build(),
				ObjectMapper.DefaultTyping.NON_FINAL);
		return new GenericJackson2JsonRedisSerializer(redisObjectMapper);
	}

	/**
	 * 创建并返回HashOperations实例
	 * @param redisTemplate Redis模板
	 * @return HashOperations实例
	 */
	@Bean
	public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForHash();
	}

	/**
	 * 创建并返回用于操作Redis String类型数据的ValueOperations实例
	 * @param redisTemplate Redis模板，用于操作Redis
	 * @return ValueOperations实例，提供对Redis String类型数据的操作
	 */
	@Bean
	public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForValue();
	}

	/**
	 * 创建并返回ListOperations实例
	 * @param redisTemplate Redis模板
	 * @return ListOperations实例
	 */
	@Bean
	public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForList();
	}

	/**
	 * 创建并返回SetOperations实例
	 * @param redisTemplate Redis模板
	 * @return SetOperations实例
	 */
	@Bean
	public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForSet();
	}

	/**
	 * 创建并返回ZSetOperations实例
	 * @param redisTemplate Redis模板对象
	 * @return ZSetOperations实例
	 */
	@Bean
	public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForZSet();
	}

}
