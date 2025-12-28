package io.github.jacorycyjin.smartlibrary.backend.service;

import io.github.jacorycyjin.smartlibrary.backend.dto.UserDTO;
import io.github.jacorycyjin.smartlibrary.backend.form.UserSearchForm;

import java.util.List;

/**
 * @author Jacory
 * @date 2025/12/11
 */
public interface UserService {

    /**
     * 登录
     * 
     * @param phoneOrEmail
     * @param password
     * @return 是否登录成功
     */
    Boolean login(String phoneOrEmail, String password);

    /**
     * 注册
     * 
     * @param phoneOrEmail
     * @param password
     * @param confirmPassword
     * @return 是否注册成功
     */
    Boolean register(String phoneOrEmail, String password, String confirmPassword);

    /**
     * 搜索用户（支持多条件查询）
     * 
     * @param searchReq
     * @return 用户列表
     */
    List<UserDTO> searchUsers(UserSearchForm searchReq);
}
