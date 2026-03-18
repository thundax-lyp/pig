
package com.github.thundax.bacon.common.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Redis 配置类
 *
 */
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
