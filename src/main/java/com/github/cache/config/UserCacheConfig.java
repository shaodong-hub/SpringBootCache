package com.github.cache.config;

import com.github.cache.pojo.UserCacheDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

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

    @Bean
    public RedisTemplate<String, UserCacheDTO> getUserCacheDTORedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, UserCacheDTO> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setDefaultSerializer(new Jackson2JsonRedisSerializer<>(UserCacheDTO.class));
        return redisTemplate;
    }

    /**
     * @param factory RedisConnectionFactory
     * @return RedisCacheManager
     */
    @Bean
    @Primary
    public RedisCacheManager userCacheDTORedisCacheManager(RedisConnectionFactory factory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.lockingRedisCacheWriter(factory);
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        return new RedisCacheManager(redisCacheWriter, cacheConfiguration);

    }


}
