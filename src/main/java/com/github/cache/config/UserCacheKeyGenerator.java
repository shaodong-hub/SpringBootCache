package com.github.cache.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * <p>
 * 创建时间为 下午2:45-2019/2/1
 * 项目名称 SpringBootCache
 * </p>
 *
 * @author shao
 * @version 0.0.1
 * @since 0.0.1
 */

@Configuration
public class UserCacheKeyGenerator {


    @Bean("UserKeyGenerator")
    public KeyGenerator getKeyGenerator() {
        System.out.println("getKeyGenerator");
        return (target, method, params) -> method.getName() + "[" + Arrays.asList(params).toString() + "]";
    }

}
