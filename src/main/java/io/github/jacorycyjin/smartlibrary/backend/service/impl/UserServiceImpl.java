package io.github.jacorycyjin.smartlibrary.backend.service.impl;

import io.github.jacorycyjin.smartlibrary.backend.entity.User;
import io.github.jacorycyjin.smartlibrary.backend.form.UserSearchForm;
import io.github.jacorycyjin.smartlibrary.backend.dto.UserDTO;
import io.github.jacorycyjin.smartlibrary.backend.mapper.UserMapper;
import io.github.jacorycyjin.smartlibrary.backend.service.UserService;
import jakarta.annotation.Resource;
import io.github.jacorycyjin.smartlibrary.backend.common.util.ValidationUtil;
import io.github.jacorycyjin.smartlibrary.backend.common.util.UUIDUtil;
import org.springframework.stereotype.Service;
import io.github.jacorycyjin.smartlibrary.backend.common.exception.BusinessException;
import io.github.jacorycyjin.smartlibrary.backend.common.enums.ApiCode;

import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jacory
 * @date 2025/12/11
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 登录
     *
     * @param phoneOrEmail
     * @param password
     * @return 是否登录成功
     */
    @Override
    public Boolean login(String phoneOrEmail, String password) {
        // 使用 searchUsers 方法查找用户
        UserSearchForm searchForm = new UserSearchForm();
        searchForm.setPhoneOrEmail(phoneOrEmail);
        searchForm.setDeleted(0);
        searchForm.setLimit(1);

        List<UserDTO> users = searchUser(searchForm);
        if (users == null || users.isEmpty()) {
            return false;
        }

        UserDTO userDTO = users.get(0);
        return userDTO.getPassword().equals(password);
    }

    /**
     * 注册
     *
     * @param phoneOrEmail
     * @param password
     * @param confirmPassword
     * @return 是否注册成功
     */
    @Override
    public Boolean register(String phoneOrEmail, String password, String confirmPassword) {
        ValidationUtil.validateNotEmpty(phoneOrEmail, "手机号/邮箱");
        ValidationUtil.validateNotEmpty(password, "密码");
        ValidationUtil.validateNotEmpty(confirmPassword, "确认密码");
        ValidationUtil.validatePasswordFormat(password);
        if (!password.equals(confirmPassword)) {
            throw new BusinessException(ApiCode.PARAM_INVALID.getCode(), "两次输入的密码不一致");
        }

        // 使用 searchUsers 方法检查用户是否已存在
        UserSearchForm searchForm = new UserSearchForm();
        searchForm.setPhoneOrEmail(phoneOrEmail);
        searchForm.setDeleted(0);
        searchForm.setLimit(1);

        List<UserDTO> existingUsers = searchUser(searchForm);
        if (existingUsers != null && !existingUsers.isEmpty()) {
            if (ValidationUtil.validatePhoneOrEmailFormat(phoneOrEmail)) {
                throw new BusinessException(ApiCode.PARAM_INVALID.getCode(), "该邮箱已被注册");
            } else {
                throw new BusinessException(ApiCode.PARAM_INVALID.getCode(), "该手机号已被注册");
            }
        }
        String userId = UUIDUtil.generateUUID();
        User user = User.builder()
                .userId(userId)
                .username("用户" + userId.substring(0, 8))
                .phone(phoneOrEmail)
                .password(password)
                .email(phoneOrEmail)
                .avatarUrl(null)
                .role(0)
                .bio(null)
                .status(0)
                .ctime(LocalDateTime.now())
                .mtime(LocalDateTime.now())
                .build();
        return userMapper.insertUser(user) > 0;
    }

    /**
     * 搜索用户（支持多条件查询）
     *
     * @param searchForm 查询条件
     * @return 用户列表
     */
    @Override
    public List<UserDTO> searchUser(UserSearchForm searchForm) {
        Map<String, Object> params = new HashMap<>();

        // 处理 phoneOrEmail 参数
        if (searchForm.getPhoneOrEmail() != null && !searchForm.getPhoneOrEmail().isEmpty()) {
            if (ValidationUtil.validatePhoneOrEmailFormat(searchForm.getPhoneOrEmail())) {
                params.put("email", searchForm.getPhoneOrEmail());
            } else {
                params.put("phone", searchForm.getPhoneOrEmail());
            }
        }

        // 添加其他查询参数
        if (searchForm.getUserId() != null && !searchForm.getUserId().isEmpty()) {
            params.put("userId", searchForm.getUserId());
        }
        if (searchForm.getUsername() != null && !searchForm.getUsername().isEmpty()) {
            params.put("username", searchForm.getUsername());
        }
        if (searchForm.getPhone() != null && !searchForm.getPhone().isEmpty()) {
            params.put("phone", searchForm.getPhone());
        }
        if (searchForm.getEmail() != null && !searchForm.getEmail().isEmpty()) {
            params.put("email", searchForm.getEmail());
        }
        if (searchForm.getRole() != null) {
            params.put("role", searchForm.getRole());
        }
        if (searchForm.getStatus() != null) {
            params.put("status", searchForm.getStatus());
        }
        if (searchForm.getDeleted() != null) {
            params.put("deleted", searchForm.getDeleted());
        } else {
            // 默认只查询未删除的用户
            params.put("deleted", 0);
        }
        if (searchForm.getLimit() != null && searchForm.getLimit() > 0) {
            params.put("limit", searchForm.getLimit());
        }

        List<User> users = userMapper.searchUser(params);
        return users.stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
