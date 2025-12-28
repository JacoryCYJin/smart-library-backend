package io.github.jacorycyjin.smartlibrary.backend.mapper;

import io.github.jacorycyjin.smartlibrary.backend.entity.User;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;
/**
 * @author Jacory
 * @date 2025/12/11
 */
@Mapper
public interface UserMapper {

    /**
     * 查询用户
     * 
     * @param params
     * @return 用户列表
     */
    List<User> findUser(Map<String, Object> params);

    /**
     * 插入用户
     * 
     * @param user
     * @return 是否插入成功
     */
    int insertUser(User user);
}
