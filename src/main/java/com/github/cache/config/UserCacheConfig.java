package com.github.cache.config;

import com.github.cache.pojo.UserCacheDO;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.time.Duration;

/**
 * <p>
 * 创建时间为 下午4:16-2019/2/1
 * 项目名称 SpringBootCache
 * </p>
 *
 * @author shao
 * @version 0.0.1
 * @since 0.0.1
 */

@Configuration
public class UserCacheConfig {

    /**
     * RedisTemplate
     *
     * @param factory RedisConnectionFactory
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<String, UserCacheDO> getUserCacheDoRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, UserCacheDO> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setDefaultSerializer(new Jackson2JsonRedisSerializer<>(UserCacheDO.class));
        return redisTemplate;
    }

    /**
     * CacheManager 获取缓存管理器
     *
     * @param factory RedisConnectionFactory
     * @return RedisCacheManager
     */
    @Bean("RedisCacheManager")
    public CacheManager userCacheDoRedisCacheManager(RedisConnectionFactory factory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.lockingRedisCacheWriter(factory);
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        return new RedisCacheManager(redisCacheWriter, cacheConfiguration);
    }


    /**
     * CacheManager 带过期时间的缓存
     *
     * @param factory RedisConnectionFactory
     * @return CacheManager
     */
    @Bean
    @Primary
    public CacheManager getCacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
            .defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(1));
        return RedisCacheManager
            .builder(factory)
            .cacheDefaults(redisCacheConfiguration)
            .build();
    }


}
