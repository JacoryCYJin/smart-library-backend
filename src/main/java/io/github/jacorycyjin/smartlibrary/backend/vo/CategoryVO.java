package io.github.jacorycyjin.smartlibrary.backend.vo;

import io.github.jacorycyjin.smartlibrary.backend.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 图书分类 VO
 * 
 * @author Jacory
 * @date 2025/12/28
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class CategoryVO {

    /**
     * 分类业务ID
     */
    private String categoryId;

    /**
     * 父分类ID
     */
    private String parentCategoryId;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 层级
     */
    private Integer level;

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
     * 从 DTO 转换为 VO
     */
    public static CategoryVO fromDTO(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }
        return CategoryVO.builder()
                .categoryId(dto.getCategoryId())
                .parentCategoryId(dto.getParentCategoryId())
                .name(dto.getName())
                .level(dto.getLevel())
                .sortOrder(dto.getSortOrder())
                .ctime(dto.getCtime())
                .mtime(dto.getMtime())
                .build();
    }
}
