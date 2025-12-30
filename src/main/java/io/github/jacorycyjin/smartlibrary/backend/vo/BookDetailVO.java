package io.github.jacorycyjin.smartlibrary.backend.vo;

import io.github.jacorycyjin.smartlibrary.backend.dto.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
     * 从 DTO 转换为 VO
     */
    public static BookDetailVO fromDTO(BookDTO dto) {
        if (dto == null) {
            return null;
        }
        return BookDetailVO.builder()
                .bookId(dto.getBookId())
                .isbn(dto.getIsbn())
                .title(dto.getTitle())
                .subTitle(dto.getSubTitle())
                .authorName(dto.getAuthorName())
                .publisher(dto.getPublisher())
                .pubDate(dto.getPubDate())
                .price(dto.getPrice())
                .pageCount(dto.getPageCount())
                .coverUrl(dto.getCoverUrl())
                .summary(dto.getSummary())
                .sourceOrigin(dto.getSourceOrigin())
                .sourceUrl(dto.getSourceUrl())
                .sourceScore(dto.getSourceScore())
                .sentimentScore(dto.getSentimentScore())
                .ctime(dto.getCtime())
                .mtime(dto.getMtime())
                .build();
    }
}
