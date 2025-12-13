package io.github.jacorycyjin.smartlibrary.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.jacorycyjin.smartlibrary.backend.service.UserService;
import io.github.jacorycyjin.smartlibrary.backend.dto.UserDTO;
import io.github.jacorycyjin.smartlibrary.backend.common.response.Result;
import io.github.jacorycyjin.smartlibrary.backend.common.enums.ApiCode;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import io.github.jacorycyjin.smartlibrary.backend.vo.UserVO;
import io.github.jacorycyjin.smartlibrary.backend.common.form.LoginForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
     * 根据手机号或邮箱查找用户
     * 
     * @param phoneOrEmail
     * @return UserVO
     */
    @GetMapping("/find-user-by-phone-or-email")
    public Result<UserVO> findUserByPhoneOrEmail(@RequestParam String phoneOrEmail) {
        UserDTO userDTO = userService.findUserByPhoneOrEmail(phoneOrEmail);
        if (userDTO == null) {
            return Result.fail(ApiCode.PARAM_INVALID.getCode(), "用户不存在");
        }
        return Result.success(UserVO.fromDTO(userDTO));
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
}
