package com.github.cache.pojo;


import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

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


@Setter
@Getter
@Document(collection = "UserCacheDO")
public class UserCacheDO implements Serializable {

    private static final long serialVersionUID = -8116446033357952120L;

    @Indexed(unique = true)
    private String name;

    @Indexed
    private Integer age;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
