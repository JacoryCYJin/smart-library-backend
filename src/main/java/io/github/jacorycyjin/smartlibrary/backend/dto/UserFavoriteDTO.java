package io.github.jacorycyjin.smartlibrary.backend.dto;

import io.github.jacorycyjin.smartlibrary.backend.entity.UserFavorite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户收藏 DTO
 * 
 * @author Jacory
 * @date 2025/12/28
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class UserFavoriteDTO {

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
     * 从实体类转换为 DTO
     */
    public static UserFavoriteDTO fromEntity(UserFavorite favorite) {
        if (favorite == null) {
            return null;
        }
        return UserFavoriteDTO.builder()
                .userId(favorite.getUserId())
                .bookId(favorite.getBookId())
                .ctime(favorite.getCtime())
                .mtime(favorite.getMtime())
                .build();
    }
}
