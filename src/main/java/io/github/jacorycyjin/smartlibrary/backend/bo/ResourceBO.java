package io.github.jacorycyjin.smartlibrary.backend.bo;

import io.github.jacorycyjin.smartlibrary.backend.common.enums.ResourceType;
import io.github.jacorycyjin.smartlibrary.backend.dto.CategoryDTO;
import io.github.jacorycyjin.smartlibrary.backend.dto.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 资源业务对象（图书+文献）
 * 
 * @author Jacory
 * @date 2025/01/19
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ResourceBO {

    /**
     * 资源业务ID
     */
    private String resourceId;

    /**
     * 资源类型（1=图书，2=文献）
     */
    private ResourceType type;

    /**
     * 标题/书名
     */
    private String title;

    /**
     * 副标题
     */
    private String subTitle;

    /**
     * 作者名称快照
     */
    private String authorName;

    /**
     * 封面URL
     */
    private String coverUrl;

    /**
     * 简介/摘要
     */
    private String summary;

    /**
     * 出版/发表日期
     */
    private LocalDate pubDate;

    // ========== 图书特有字段 ==========
    
    /**
     * ISBN（仅图书）
     */
    private String isbn;

    /**
     * 出版社（仅图书）
     */
    private String publisher;

    /**
     * 价格（仅图书）
     */
    private BigDecimal price;

    /**
     * 页数（仅图书）
     */
    private Integer pageCount;

    // ========== 文献特有字段 ==========
    
    /**
     * DOI标识（仅文献）
     */
    private String doi;

    /**
     * 期刊名称（仅文献）
     */
    private String journal;

    /**
     * PDF文件URL（仅文献）
     */
    private String fileUrl;

    /**
     * 文件类型（仅文献）
     */
    private String fileType;

    // ========== 通用字段 ==========
    
    /**
     * 数据来源
     */
    private String sourceOrigin;

    /**
     * 原站链接
     */
    private String sourceUrl;

    /**
     * 原站评分
     */
    private BigDecimal sourceScore;

    /**
     * NLP情感分析评分
     */
    private BigDecimal sentimentScore;

    /**
     * 总浏览量
     */
    private Integer viewCount;

    /**
     * 总评论数
     */
    private Integer commentCount;

    /**
     * 总收藏数
     */
    private Integer starCount;

    /**
     * 分类列表
     */
    private List<CategoryDTO> categories;

    /**
     * 标签列表
     */
    private List<TagDTO> tags;

    /**
     * 创建时间
     */
    private LocalDateTime ctime;

    /**
     * 更新时间
     */
    private LocalDateTime mtime;
}
