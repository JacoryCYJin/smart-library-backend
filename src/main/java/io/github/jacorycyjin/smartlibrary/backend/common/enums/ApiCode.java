package io.github.jacorycyjin.smartlibrary.backend.common.enums;

/**
 * @author Jacory
 * @date 2025/12/10
 */
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiCode {

    SUCCESS(0, "成功"),
    SERVER_ERROR(-500, "服务器错误"),
    NO_AUTH_ERROR(401, "无访问权限"),
    PARAM_INVALID(-400, "参数错误"),
    BOOK_NOT_FOUND(404, "图书不存在");

    private final Integer code;
    private final String message;

}
