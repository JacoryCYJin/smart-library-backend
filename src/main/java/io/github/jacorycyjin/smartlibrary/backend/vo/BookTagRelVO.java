package io.github.jacorycyjin.smartlibrary.backend.vo;

import io.github.jacorycyjin.smartlibrary.backend.dto.BookTagRelDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;

/**
 * 图书-标签关联 VO
 * 
 * @author Jacory
 * @date 2025/12/28
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class BookTagRelVO {

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
     * 从 DTO 转换为 VO
     */
    public static BookTagRelVO fromDTO(BookTagRelDTO dto) {
        if (dto == null) {
            return null;
        }
        return BookTagRelVO.builder()
                .bookId(dto.getBookId())
                .tagId(dto.getTagId())
                .weight(dto.getWeight())
                .build();
    }
}
