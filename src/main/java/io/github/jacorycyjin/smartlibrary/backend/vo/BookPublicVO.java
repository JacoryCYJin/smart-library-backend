package io.github.jacorycyjin.smartlibrary.backend.vo;

import io.github.jacorycyjin.smartlibrary.backend.dto.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

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
     * 从 DTO 转换为 VO
     */
    public static BookPublicVO fromDTO(BookDTO dto) {
        if (dto == null) {
            return null;
        }
        return BookPublicVO.builder()
                .bookId(dto.getBookId())
                .title(dto.getTitle())
                .subTitle(dto.getSubTitle())
                .authorName(dto.getAuthorName())
                .publisher(dto.getPublisher())
                .pubDate(dto.getPubDate())
                .price(dto.getPrice())
                .coverUrl(dto.getCoverUrl())
                .sourceScore(dto.getSourceScore())
                .build();
    }
}
