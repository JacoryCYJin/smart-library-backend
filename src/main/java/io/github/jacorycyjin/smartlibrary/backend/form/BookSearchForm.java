package io.github.jacorycyjin.smartlibrary.backend.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import io.github.jacorycyjin.smartlibrary.backend.common.form.PageQueryForm;
import java.time.LocalDate;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSearchForm extends PageQueryForm {

    /**
     * 关键词搜索（合并：书名、作者、出版社、ISBN）
     */
    private String keyword;

    /**
     * 分类ID（筛选用，前端下拉框）
     */
    private Long categoryId;

    /**
     * 标签ID（筛选用，前端下拉框）
     */
    private Long tagId;

    /**
     * 最低价格（筛选用）
     */
    private BigDecimal minPrice;

    /**
     * 最高价格（筛选用）
     */
    private BigDecimal maxPrice;

    /**
     * 出版日期-开始（筛选用）
     */
    private LocalDate pubDateStart;

    /**
     * 出版日期-结束（筛选用）
     */
    private LocalDate pubDateEnd;

    /**
     * 最低原站评分（筛选用）
     */
    private BigDecimal minSourceScore;

    /**
     * 最低情感分（筛选用）
     */
    private BigDecimal minSentimentScore;
    
}
