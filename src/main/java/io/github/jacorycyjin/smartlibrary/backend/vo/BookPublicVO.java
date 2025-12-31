package io.github.jacorycyjin.smartlibrary.backend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import io.github.jacorycyjin.smartlibrary.backend.bo.BookBO;

/**
 * 图书信息公开 VO（列表页用）
 * 
 * @author Jacory
 * @date 2025/12/28
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@SuperBuilder
public class BookPublicVO {

    /**
     * 图书业务ID
     */
    private String bookId;

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
     * 封面URL
     */
    private String coverUrl;

    /**
     * 原站评分
     */
    private BigDecimal sourceScore;

    /**
     * NLP情感分 (核心卖点！列表页显示心情指数)
     */
    private BigDecimal sentimentScore;

    /**
     * 分类名称 (用于显示 "计算机" 或 "科幻")
     */
    private String categoryName;
    
    /**
     * 分类ID (用于点击分类名跳转)
     */
    private String categoryId;

    /**
     * 核心标签列表 (通常只放前3个，比如 [高分, 烧脑, 刘慈欣])
     */
    private List<TagSimpleVO> tags;

    /**
     * 从 BO 转换为 VO
     */
    public static BookPublicVO fromBO(BookBO bo) {
        if (bo == null) {
            return null;
        }
        
        // 转换标签列表
        List<TagSimpleVO> tagVOs = null;
        if (bo.getTags() != null && !bo.getTags().isEmpty()) {
            tagVOs = bo.getTags().stream()
                    .filter(tag -> tag.getTagId() != null) // 过滤空标签
                    .limit(3) // 只取前3个标签
                    .map(tag -> TagSimpleVO.builder()
                            .tagId(tag.getTagId())
                            .name(tag.getName())
                            .type(tag.getType())
                            .build())
                    .collect(Collectors.toList());
        }
        
        return BookPublicVO.builder()
                .bookId(bo.getBookId())
                .title(bo.getTitle())
                .subTitle(bo.getSubTitle())
                .authorName(bo.getAuthorName())
                .publisher(bo.getPublisher())
                .pubDate(bo.getPubDate())
                .price(bo.getPrice())
                .coverUrl(bo.getCoverUrl())
                .sourceScore(bo.getSourceScore())
                .sentimentScore(bo.getSentimentScore())
                .categoryName(bo.getCategoryName())
                .categoryId(bo.getCategoryId())
                .tags(tagVOs)
                .build();
    }

}
