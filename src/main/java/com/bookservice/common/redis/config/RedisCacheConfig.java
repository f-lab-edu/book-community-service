package com.bookservice.common.redis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisCacheConfig {
	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
		RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
														.disableCachingNullValues()
														//Redis에 Key를 저장할 때 String으로 직렬화해서 저장
														.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
														//Redis에 Value를 저장할 때 Json으로 직렬화해서 저장
														.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(createObjectMapper())))
														.entryTtl(Duration.ofDays(1L));

		return RedisCacheManager
					   .RedisCacheManagerBuilder
					   .fromConnectionFactory(redisConnectionFactory)
					   .cacheDefaults(configuration)
					   .build();
	}

	private static ObjectMapper createObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.activateDefaultTyping(
				BasicPolymorphicTypeValidator.builder()
						.allowIfBaseType(Object.class)
						.build(),
				ObjectMapper.DefaultTyping.NON_FINAL
		);
		return objectMapper;
	}
}
