package io.github.jacorycyjin.smartlibrary.backend.service;

import io.github.jacorycyjin.smartlibrary.backend.dto.UserDTO;
/**
 * @author Jacory
 * @date 2025/12/11
 */
public interface UserService {
    UserDTO findUserByPhoneOrEmail(String phoneOrEmail);
}
