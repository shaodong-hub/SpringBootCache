package com.github.cache.controller;

import com.github.cache.pojo.UserCacheDTO;
import com.github.cache.service.UserCacheService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    private final UserCacheService service;

    public UserCacheController(UserCacheService service) {
        this.service = service;
    }

    @GetMapping("cache/{name}")
    public UserCacheDTO findByName(@PathVariable String name) {
        return service.get(name);
    }

    @PostMapping("cache")
    public UserCacheDTO save(@RequestBody UserCacheDTO userCacheDTO) {
        return service.save(userCacheDTO);
    }

    @PutMapping("cache")
    public UserCacheDTO update(@RequestBody UserCacheDTO userCacheDTO){
        return service.update(userCacheDTO);
    }

}