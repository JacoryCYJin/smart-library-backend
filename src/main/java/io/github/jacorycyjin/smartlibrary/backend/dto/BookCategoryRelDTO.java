package io.github.jacorycyjin.smartlibrary.backend.dto;

import io.github.jacorycyjin.smartlibrary.backend.entity.BookCategoryRel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 图书-分类关联 DTO
 * 
 * @author Jacory
 * @date 2025/12/28
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class BookCategoryRelDTO {

    /**
     * 图书业务ID
     */
    private String bookId;

    /**
     * 分类业务ID
     */
    private String categoryId;

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
    public static BookCategoryRelDTO fromEntity(BookCategoryRel rel) {
        if (rel == null) {
            return null;
        }
        return BookCategoryRelDTO.builder()
                .bookId(rel.getBookId())
                .categoryId(rel.getCategoryId())
                .ctime(rel.getCtime())
                .mtime(rel.getMtime())
                .build();
    }
}
