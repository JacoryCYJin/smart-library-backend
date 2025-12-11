package io.github.jacorycyjin.smartlibrary.backend.vo;

import io.github.jacorycyjin.smartlibrary.backend.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Jacory
 * @date 2025/12/11
 */

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class UserVO {

    /**
     * 用户UUID，全球唯一标识
     */
    private String userId;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 头像URL
     */
    private String avatarUrl;

    /**
     * 用户简介
     */
    private String bio;

    /**
     * 注册时间
     */
    private LocalDateTime ctime;
    
    public static UserVO fromDTO(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        return UserVO.builder()
                .userId(userDTO.getUserId())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .phone(userDTO.getPhone())
                .email(userDTO.getEmail())
                .avatarUrl(userDTO.getAvatarUrl())
                .bio(userDTO.getBio())
                .ctime(userDTO.getCtime())
                .build();
    }
}
