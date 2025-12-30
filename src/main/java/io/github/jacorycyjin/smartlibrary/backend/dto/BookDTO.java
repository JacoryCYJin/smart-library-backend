package io.github.jacorycyjin.smartlibrary.backend.dto;

import io.github.jacorycyjin.smartlibrary.backend.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 图书信息 DTO
 * 
 * @author Jacory
 * @date 2025/12/28
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class BookDTO {

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
     * 从实体类转换为 DTO
     */
    public static BookDTO fromEntity(Book book) {
        if (book == null) {
            return null;
        }
        return BookDTO.builder()
                .bookId(book.getBookId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .subTitle(book.getSubTitle())
                .authorName(book.getAuthorName())
                .publisher(book.getPublisher())
                .pubDate(book.getPubDate())
                .price(book.getPrice())
                .pageCount(book.getPageCount())
                .coverUrl(book.getCoverUrl())
                .summary(book.getSummary())
                .sourceOrigin(book.getSourceOrigin())
                .sourceUrl(book.getSourceUrl())
                .sourceScore(book.getSourceScore())
                .sentimentScore(book.getSentimentScore())
                .ctime(book.getCtime())
                .mtime(book.getMtime())
                .build();
    }
}
