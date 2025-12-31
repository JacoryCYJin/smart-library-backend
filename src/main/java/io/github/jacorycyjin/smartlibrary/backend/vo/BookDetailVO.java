package io.github.jacorycyjin.smartlibrary.backend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 图书信息详情 VO（详情页/管理用）
 * 
 * @author Jacory
 * @date 2025/12/28
 */
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class BookDetailVO extends BookPublicVO {

    /**
     * ISBN
     */
    private String isbn;

    /**
     * 页数
     */
    private Integer pageCount;

    /**
     * 简介
     */
    private String summary;

    /**
     * 来源
     */
    private String sourceOrigin;

    /**
     * 来源链接
     */
    private String sourceUrl;

    /**
     * 创建时间
     */
    private LocalDateTime ctime;

    /**
     * 更新时间
     */
    private LocalDateTime mtime;

    /**
     * 完整分类层级路径（从根到叶子）
     * 例如：["计算机", "编程语言", "Java"]
     */
    private List<CategoryVO> categoryPath;

    /**
     * 所有标签列表（不限制数量）
     */
    private List<TagSimpleVO> allTags;
}
