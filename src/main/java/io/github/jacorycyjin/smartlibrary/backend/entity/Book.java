package io.github.jacorycyjin.smartlibrary.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 图书信息实体类
 * 
 * @author Jacory
 * @date 2025/12/28
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class Book {

    /**
     * 主键ID，自增
     */
    private Long id;

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
     * 是否删除：0=未删除，1=已删除
     */
    private Integer deleted;
}
