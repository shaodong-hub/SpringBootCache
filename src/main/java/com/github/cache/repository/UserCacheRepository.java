package com.github.cache.repository;

import com.github.cache.pojo.UserCacheDO;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * <p>
 * 创建时间为 上午12:58-2019/2/1
 * 项目名称 SpringBootCache
 * </p>
 *
 * @author shao
 * @version 0.0.1
 * @since 0.0.1
 */


public interface UserCacheRepository extends PagingAndSortingRepository<UserCacheDO, String> {

    /**
     * 根据用户名查询单个用户
     *
     * @param name 用户名
     * @return UserCacheDO
     */
    UserCacheDO findUserCacheDOByNameIs(String name);

    /**
     * 删除单个用户
     *
     * @param name 用户名
     */
    void deleteUserCacheDOByNameIs(String name);

}