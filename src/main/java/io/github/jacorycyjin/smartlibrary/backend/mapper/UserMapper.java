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
    List<User> findUser(Map<String, Object> params);
}
