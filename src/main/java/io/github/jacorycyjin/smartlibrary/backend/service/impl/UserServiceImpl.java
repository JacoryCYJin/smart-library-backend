package io.github.jacorycyjin.smartlibrary.backend.service.impl;

import io.github.jacorycyjin.smartlibrary.backend.entity.User;
import io.github.jacorycyjin.smartlibrary.backend.dto.UserDTO;
import io.github.jacorycyjin.smartlibrary.backend.mapper.UserMapper;
import io.github.jacorycyjin.smartlibrary.backend.service.UserService;
import jakarta.annotation.Resource;
import io.github.jacorycyjin.smartlibrary.backend.common.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Jacory
 * @date 2025/12/11
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 根据手机号或邮箱查找用户
     * 
     * @param phoneOrEmail
     * @return UserDTO
     */
    @Override
    public UserDTO findUserByPhoneOrEmail(String phoneOrEmail) {

        ValidationUtil.validateNotEmpty(phoneOrEmail, "手机号/邮箱");
        Map<String, Object> params = new HashMap<>();
        params.put("deleted", 0);
        params.put("limit", 1);
        if (ValidationUtil.validatePhoneOrEmailFormat(phoneOrEmail)) {
            params.put("email", phoneOrEmail);
        } else {
            params.put("phone", phoneOrEmail);
        }
        User user = userMapper.findUser(params).get(0);
        if (user == null) {
            return null;
        }
        return UserDTO.fromEntity(user);
    }

    /**
     * 登录
     * 
     * @param phoneOrEmail
     * @param password
     * @return 是否登录成功
     */
    @Override
    public Boolean login(String phoneOrEmail, String password) {
        UserDTO userDTO = findUserByPhoneOrEmail(phoneOrEmail);
        if (userDTO == null) {
            return false;
        }
        return userDTO.getPassword().equals(password);
    }
}
