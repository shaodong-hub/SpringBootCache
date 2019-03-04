package com.github.cache.service;

import com.github.cache.pojo.UserCacheDO;

/**
 * <p>
 * 创建时间为 16:08-2019-03-04
 * 项目名称 SpringBootCache
 * </p>
 *
 * @author shao
 * @version 0.0.1
 * @since 0.0.1
 */


public interface IUserCacheService {


    /**
     * @param userCacheDTO
     * @return
     */
    UserCacheDO save(UserCacheDO userCacheDTO);

    /**
     * @param name
     * @return
     */
    UserCacheDO get(String name);


    /**
     * @param name
     */
    void delete(String name);

    /**
     * @param name
     * @return
     */
    UserCacheDO findByName(String name);

}
