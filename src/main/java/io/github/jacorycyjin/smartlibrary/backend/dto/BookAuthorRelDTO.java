package io.github.jacorycyjin.smartlibrary.backend.dto;

import io.github.jacorycyjin.smartlibrary.backend.entity.BookAuthorRel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 图书-作者关联 DTO
 * 
 * @author Jacory
 * @date 2025/12/28
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class BookAuthorRelDTO {

    /**
     * 图书业务ID
     */
    private String bookId;

    /**
     * 作者业务ID
     */
    private String authorId;

    /**
     * 角色（作者、译者等）
     */
    private String role;

    /**
     * 排序权重
     */
    private Integer sortOrder;

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
    public static BookAuthorRelDTO fromEntity(BookAuthorRel rel) {
        if (rel == null) {
            return null;
        }
        return BookAuthorRelDTO.builder()
                .bookId(rel.getBookId())
                .authorId(rel.getAuthorId())
                .role(rel.getRole())
                .sortOrder(rel.getSortOrder())
                .ctime(rel.getCtime())
                .mtime(rel.getMtime())
                .build();
    }
}
