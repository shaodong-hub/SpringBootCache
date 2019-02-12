package com.github.cache.repository;

import com.github.cache.pojo.UserCacheDTO;
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


public interface UserCacheRepository extends PagingAndSortingRepository<UserCacheDTO, String> {

    UserCacheDTO findUserCacheDTOByNameIs(String name);

    void deleteUserCacheDTOByNameIs(String name);

}