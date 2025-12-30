package io.github.jacorycyjin.smartlibrary.backend.vo;

import io.github.jacorycyjin.smartlibrary.backend.dto.BookAuthorRelDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

/**
 * 图书-作者关联 VO
 * 
 * @author Jacory
 * @date 2025/12/28
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class BookAuthorRelVO {

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
     * 从 DTO 转换为 VO
     */
    public static BookAuthorRelVO fromDTO(BookAuthorRelDTO dto) {
        if (dto == null) {
            return null;
        }
        return BookAuthorRelVO.builder()
                .bookId(dto.getBookId())
                .authorId(dto.getAuthorId())
                .role(dto.getRole())
                .sortOrder(dto.getSortOrder())
                .build();
    }
}
