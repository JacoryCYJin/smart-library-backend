package io.github.jacorycyjin.smartlibrary.backend.vo;

import io.github.jacorycyjin.smartlibrary.backend.dto.BookCategoryRelDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

/**
 * 图书-分类关联 VO
 * 
 * @author Jacory
 * @date 2025/12/28
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class BookCategoryRelVO {

    /**
     * 图书业务ID
     */
    private String bookId;

    /**
     * 分类业务ID
     */
    private String categoryId;

    /**
     * 从 DTO 转换为 VO
     */
    public static BookCategoryRelVO fromDTO(BookCategoryRelDTO dto) {
        if (dto == null) {
            return null;
        }
        return BookCategoryRelVO.builder()
                .bookId(dto.getBookId())
                .categoryId(dto.getCategoryId())
                .build();
    }
}
