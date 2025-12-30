package io.github.jacorycyjin.smartlibrary.backend.vo;

import io.github.jacorycyjin.smartlibrary.backend.dto.UserFavoriteDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户收藏 VO
 * 
 * @author Jacory
 * @date 2025/12/28
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class UserFavoriteVO {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 图书ID
     */
    private String bookId;

    /**
     * 创建时间
     */
    private LocalDateTime ctime;

    /**
     * 更新时间
     */
    private LocalDateTime mtime;

    /**
     * 从 DTO 转换为 VO
     */
    public static UserFavoriteVO fromDTO(UserFavoriteDTO dto) {
        if (dto == null) {
            return null;
        }
        return UserFavoriteVO.builder()
                .userId(dto.getUserId())
                .bookId(dto.getBookId())
                .ctime(dto.getCtime())
                .mtime(dto.getMtime())
                .build();
    }
}
