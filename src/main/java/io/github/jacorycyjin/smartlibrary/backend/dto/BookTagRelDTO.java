package io.github.jacorycyjin.smartlibrary.backend.dto;

import io.github.jacorycyjin.smartlibrary.backend.entity.BookTagRel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 图书-标签关联 DTO
 * 
 * @author Jacory
 * @date 2025/12/28
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class BookTagRelDTO {

    /**
     * 图书业务ID
     */
    private String bookId;

    /**
     * 标签业务ID
     */
    private String tagId;

    /**
     * 权重
     */
    private BigDecimal weight;

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
    public static BookTagRelDTO fromEntity(BookTagRel rel) {
        if (rel == null) {
            return null;
        }
        return BookTagRelDTO.builder()
                .bookId(rel.getBookId())
                .tagId(rel.getTagId())
                .weight(rel.getWeight())
                .ctime(rel.getCtime())
                .mtime(rel.getMtime())
                .build();
    }
}
