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
/**
 * @author Jacory
 * @date 2025/12/11
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/find-user-by-phone-or-email")
    public Result<UserVO> findUserByPhoneOrEmail(@RequestParam String phoneOrEmail) {
        UserDTO userDTO = userService.findUserByPhoneOrEmail(phoneOrEmail);
        if (userDTO == null) {
            return Result.fail(ApiCode.PARAM_INVALID.getCode(), "用户不存在");
        }
        return Result.success(UserVO.fromDTO(userDTO));
    }
}
