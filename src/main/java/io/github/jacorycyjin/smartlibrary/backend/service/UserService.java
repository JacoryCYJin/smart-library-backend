package io.github.jacorycyjin.smartlibrary.backend.service;

import io.github.jacorycyjin.smartlibrary.backend.dto.UserDTO;
/**
 * @author Jacory
 * @date 2025/12/11
 */
public interface UserService {

    /**
     * 根据手机号或邮箱查找用户
     * 
     * @param phoneOrEmail
     * @return UserDTO
     */
    UserDTO findUserByPhoneOrEmail(String phoneOrEmail);

    /**
     * 登录
     * 
     * @param phoneOrEmail
     * @param password
     * @return 是否登录成功
     */
    Boolean login(String phoneOrEmail, String password);
}
