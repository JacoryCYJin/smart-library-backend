package io.github.jacorycyjin.smartlibrary.backend.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 图书信息 DTO（包含分类和标签关联）
 * 
 * @author Jacory
 * @date 2025/12/31
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class BookBO {

    /**
     * 图书业务ID
     */
    private String bookId;

    /**
     * ISBN
     */
    private String isbn;

    /**
     * 书名
     */
    private String title;

    /**
     * 副标题
     */
    private String subTitle;

    /**
     * 作者姓名快照
     */
    private String authorName;

    /**
     * 出版社
     */
    private String publisher;

    /**
     * 出版日期
     */
    private LocalDate pubDate;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 页数
     */
    private Integer pageCount;

    /**
     * 封面URL
     */
    private String coverUrl;

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
     * 原站评分
     */
    private BigDecimal sourceScore;

    /**
     * NLP情感分
     */
    private BigDecimal sentimentScore;

    /**
     * 创建时间
     */
    private LocalDateTime ctime;

    /**
     * 更新时间
     */
    private LocalDateTime mtime;

    /**
     * 分类ID
     */
    private String categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 标签列表
     */
    private List<TagSimpleDTO> tags;

    /**
     * 简化的标签 DTO
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TagSimpleDTO {
        private String tagId;
        private String name;
        private Integer type;
    }
}
