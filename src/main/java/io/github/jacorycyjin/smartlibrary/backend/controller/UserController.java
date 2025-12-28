package io.github.jacorycyjin.smartlibrary.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.jacorycyjin.smartlibrary.backend.service.UserService;
import io.github.jacorycyjin.smartlibrary.backend.dto.UserDTO;
import io.github.jacorycyjin.smartlibrary.backend.form.LoginForm;
import io.github.jacorycyjin.smartlibrary.backend.form.RegisterForm;
import io.github.jacorycyjin.smartlibrary.backend.form.UserSearchForm;
import io.github.jacorycyjin.smartlibrary.backend.common.response.Result;
import io.github.jacorycyjin.smartlibrary.backend.common.enums.ApiCode;
import jakarta.annotation.Resource;
import io.github.jacorycyjin.smartlibrary.backend.vo.UserVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

/**
 * @author Jacory
 * @date 2025/12/11
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 搜索用户（支持多条件查询）
     * 
     * @param searchForm 查询条件
     * @return 用户列表
     */
    @PostMapping("/search")
    public Result<List<UserVO>> searchUsers(@RequestBody UserSearchForm searchForm) {
        List<UserDTO> userDTOs = userService.searchUsers(searchForm);
        if (userDTOs == null || userDTOs.isEmpty()) {
            return Result.fail(ApiCode.PARAM_INVALID.getCode(), "未找到符合条件的用户");
        }
        List<UserVO> userVOs = userDTOs.stream()
                .map(UserVO::fromDTO)
                .toList();
        return Result.success(userVOs);
    }

    /**
     * 登录
     * 
     * @param loginForm
     * @return 是否登录成功 
     */
    @PostMapping("/login")
    public Result<Boolean> login(@RequestBody LoginForm loginForm) {
        Boolean isLogin = userService.login(loginForm.getPhoneOrEmail(), loginForm.getPassword());
        if (!isLogin) {
            return Result.fail(ApiCode.PARAM_INVALID.getCode(), "用户/密码错误");
        }
        return Result.success();
    }

    /**
     * 注册
     * 
     * @param registerForm
     * @return 是否注册成功
     */
    @PostMapping("/register")
    public Result<Boolean> register(@RequestBody RegisterForm registerForm) {
        Boolean isRegister = userService.register(registerForm.getPhoneOrEmail(), registerForm.getPassword(), registerForm.getConfirmPassword());
        if (!isRegister) {
            return Result.fail(ApiCode.PARAM_INVALID.getCode(), "注册失败");
        }
        return Result.success();
    }
}
