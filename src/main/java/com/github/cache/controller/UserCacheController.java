package com.github.cache.controller;

import com.github.cache.pojo.UserCacheDO;
import com.github.cache.service.IUserCacheService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 创建时间为 上午1:01-2019/2/1
 * 项目名称 SpringBootCache
 * </p>
 *
 * @author shao
 * @version 0.0.1
 * @since 0.0.1
 */


@RestController
public class UserCacheController {

    @Resource
    private IUserCacheService service;

    @GetMapping("cache/{name}")
    public UserCacheDO findByName(@PathVariable String name) {
        return service.get(name);
    }

    @PostMapping("cache")
    public UserCacheDO save(@RequestBody UserCacheDO userCacheDTO) {
        return service.save(userCacheDTO);
    }

}