package com.github.cache.service;

import com.github.cache.pojo.UserCacheDTO;
import com.github.cache.repository.UserCacheRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 创建时间为 上午1:00-2019/2/1
 * 项目名称 SpringBootCache
 * </p>
 *
 * @author shao
 * @version 0.0.1
 * @since 0.0.1
 */

// 抽取缓存的公共配置
@CacheConfig(cacheNames = "name")
@Service
public class UserCacheService {

    private final UserCacheRepository repository;

    public UserCacheService(UserCacheRepository repository) {
        this.repository = repository;
    }

    public UserCacheDTO save(UserCacheDTO userCacheDTO) {
        return repository.save(userCacheDTO);
    }

    /**
     * 几个属性:
     * cacheNames/value 指定缓存组件的名字;
     * key: 缓存数据视同的 key, 可以用它来指定,默认是使用方法的参数, 1-方法的返回值
     * 编写 SpEL
     * keyGenerator:key 的生成器;可以指定 key 的生产器的组件 id, key/keyGenerator:二选一使用.
     * 将方法的运行结果进行缓存, 以后再要相同的数据, 直接冲缓存中获取, 不同调用方法;
     * CacheManager 管理多个 Cache 组件, 对缓存的正常的 CRUD 操作在 Cache 组件中, 每个缓存组件中有自己唯一的名字;
     * condition 指定符合条件的情况下才缓存 condition = '#id > 0'
     * unless: 否定缓存:当unless 指定的条件为 true ,方法的返回值就不会被缓存,可以获取带结果进行判断, unless = "#result == null"
     * sync:是否使用异步缓存
     * <p>
     * 原理:
     * 1.自动配置类:{@link org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration}
     * 2.缓存的配置类
     * 0 = "{@link org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration}"
     * 1 = "{@link org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration}"
     * 2 = "{@link org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration}"
     * 3 = "{@link org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration}"
     * 4 = "{@link org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration}"
     * 5 = "{@link org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration}"
     * 6 = "{@link org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration}"
     * 7 = "{@link org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration}"
     * 8 = "{@link org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration}"[默认]
     * 9 = "{@link org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration}"
     * <p>
     * 运行流程
     * <p>
     * - @Cacheable: 1.方法运行事前, 先去查询 Cache(缓存组件),按照 cacheNames 指定的名字获取:
     * (CacheManager 先获取相应的缓存),第一次获取缓存如果没有 Cache 组件会自动创建.
     * 2.去 cache 中查找缓存的内容,使用一个 key ,默认就是方法的参数:
     * key 是按照某种策略生成的,默认使用的 keyGenerator生成的,默认使用 SimpleKeyGenerator 生成 key
     * SimpleKeyGenerator生成key 的默认策略:
     * 如果没有参数, key = new SimpleKey()
     * 如果只有一个参数, key = 参数的值
     * 如果有多个参数, key = new SimpleKey(params)
     * <p>
     * 3.没有查询到缓存就调用目标方法:
     * 4.将目标方法返回的结果放进缓存中.
     * -@Cacheable 标注的方法执行之前先检查缓存中有没有这个数据, 默认按照参数的值作为 key 去查询库,如果没有就运行方法将结果放入缓存中;
     *
     * @param name 参数的名称
     * @return 查询到的结果
     */
//    @Cacheable(value = "name", keyGenerator = "UserKeyGenerator", condition = "#a0 == 'name2'")
    @Cacheable(value = "name")
    public UserCacheDTO get(String name) {
        System.out.println("get");
        return repository.findUserCacheDTOByNameIs(name);
    }

    /**
     * -@CachePut:既调用方法,又更新缓存: 修改了数据库的某个数据, 同步更新缓存
     * 运行时机:
     * 1.先调用目标方法;
     * 2.将目标方法的结果缓存起来;
     * <p>
     * 测试步骤:
     *
     * @param userCacheDTO 需要更新的数据
     * @return 更新后的数据
     */
    @CachePut(value = "name", key = "#result.name")
    public UserCacheDTO update(UserCacheDTO userCacheDTO) {
        return repository.save(userCacheDTO);
    }

    /**
     * -@CacheEvict 清除缓存
     * key:指定要清除的数据
     * allEntries = true:指定清除这个缓存中所有的数据
     * beforeInvocation = false: 缓存的清除是否在方法之前执行
     * 默认代表缓存清除操作是在方法执行之后执行:如果出现异常缓存就不会清除
     * beforeInvocation = false: 缓存的清除是否在方法之后执行
     * 无论方法是否出现异常,缓存都清除
     *
     * @param name 参数的名称
     */
    @CacheEvict(value = "name")
    public void delete(String name) {
        repository.deleteUserCacheDTOByNameIs(name);
    }

    /**
     * -@Caching 复杂缓存配置
     *
     * @param name 参数的名称
     * @return UserCacheDTO
     */
    @Caching(
        cacheable = {
            @Cacheable(value = "name", key = "#name")
        },
        put = {
            @CachePut(value = "name", key = "#result.name"),
            @CachePut(value = "name", key = "#result.age")
        }
    )
    public UserCacheDTO findUserCacheDTOByName(String name) {
        System.out.println("get");
        return repository.findUserCacheDTOByNameIs(name);
    }

}



















