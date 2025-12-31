package io.github.jacorycyjin.smartlibrary.backend.service.impl;

import io.github.jacorycyjin.smartlibrary.backend.service.BookService;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import io.github.jacorycyjin.smartlibrary.backend.mapper.BookMapper;
import io.github.jacorycyjin.smartlibrary.backend.mapper.CategoryMapper;
import io.github.jacorycyjin.smartlibrary.backend.mapper.TagMapper;
import io.github.jacorycyjin.smartlibrary.backend.mapper.BookCategoryRelMapper;
import io.github.jacorycyjin.smartlibrary.backend.entity.Book;
import io.github.jacorycyjin.smartlibrary.backend.entity.Category;
import io.github.jacorycyjin.smartlibrary.backend.bo.BookBO;
import io.github.jacorycyjin.smartlibrary.backend.form.BookSearchForm;
import io.github.jacorycyjin.smartlibrary.backend.common.dto.PageDTO;
import io.github.jacorycyjin.smartlibrary.backend.common.dto.PageQueryDTO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * 图书服务实现（方案 3：Mapper 各回各家 + Service 组装）
 * 
 * @author Jacory
 * @date 2025/12/31
 */
@Service
public class BookServiceImpl implements BookService {

    @Resource
    private BookMapper bookMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private BookCategoryRelMapper bookCategoryRelMapper;

    @Override
    public PageDTO<BookBO> searchBook(BookSearchForm searchForm) {
        // 1. 转换并校验分页参数
        PageQueryDTO pageQuery = searchForm.toPageQueryDTO();
        
        // 2. 构建查询参数
        Map<String, Object> params = buildSearchParams(searchForm);

        // 3. 根据 fetchAll 决定是否启动分页
        List<Book> books;
        int totalCount;
        
        if (pageQuery.isFetchAll()) {
            // 查询所有数据，不分页
            books = bookMapper.searchBook(params);
            totalCount = books == null ? 0 : books.size();
        } else {
            // 启动分页（PageHelper 会拦截下一次查询）
            PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
            books = bookMapper.searchBook(params);
            PageInfo<Book> pageInfo = new PageInfo<>(books);
            totalCount = (int) pageInfo.getTotal();
        }
        
        if (books == null || books.isEmpty()) {
            return new PageDTO<>(pageQuery.getPageNum(), 0, pageQuery.getPageSize(), new ArrayList<>());
        }

        // 5. 提取图书ID列表
        List<String> bookIds = books.stream()
                .map(Book::getBookId)
                .collect(Collectors.toList());

        // 6. 批量查询图书-分类关联（BookCategoryRelMapper 负责关联表）
        List<Map<String, Object>> bookCategoryRelList = bookCategoryRelMapper.selectByBookIds(bookIds);

        // 提取分类业务ID列表（category_id 字段存储的是业务ID）
        List<String> categoryIds = bookCategoryRelList.stream()
                .map(rel -> (String) rel.get("categoryId"))
                .distinct()
                .collect(Collectors.toList());

        // 7. 批量查询分类信息（CategoryMapper 负责 Category 表，使用业务ID查询）
        Map<String, Category> categoryMap = new HashMap<>();
        if (!categoryIds.isEmpty()) {
            List<Category> categories = categoryMapper.selectByCategoryIds(categoryIds);
            categoryMap = categories.stream()
                    .collect(Collectors.toMap(Category::getCategoryId, c -> c));
        }

        // 8. 构建 bookId -> Category 的映射
        Map<String, Category> bookToCategoryMap = new HashMap<>();
        for (Map<String, Object> rel : bookCategoryRelList) {
            String bookId = (String) rel.get("bookId");
            String categoryId = (String) rel.get("categoryId");
            Category category = categoryMap.get(categoryId);
            if (category != null) {
                bookToCategoryMap.put(bookId, category);
            }
        }

        // 9. 批量查询标签信息（TagMapper 负责 Tag 表和关联表）
        List<Map<String, Object>> tagList = tagMapper.selectTagsByBookIds(bookIds);
        Map<String, List<Map<String, Object>>> tagMap = tagList.stream()
                .collect(Collectors.groupingBy(m -> (String) m.get("bookId")));

        // 10. 组装数据：Book + Category + Tags -> BookBO（使用业务ID）
        List<BookBO> bookBOs = books.stream()
                .map(book -> assembleBookBO(book, bookToCategoryMap.get(book.getBookId()), tagMap.get(book.getBookId())))
                .collect(Collectors.toList());
        
        // 11. 返回分页结果
        return new PageDTO<>(pageQuery.getPageNum(), totalCount, pageQuery.getPageSize(), bookBOs);
    }

    /**
     * 构建查询参数
     */
    private Map<String, Object> buildSearchParams(BookSearchForm searchForm) {
        Map<String, Object> params = new HashMap<>();

        // 关键词搜索
        if (searchForm.getKeyword() != null && !searchForm.getKeyword().isEmpty()) {
            params.put("keyword", searchForm.getKeyword());
        }

        // 筛选条件
        if (searchForm.getCategoryId() != null) {
            params.put("categoryId", searchForm.getCategoryId());
        }
        if (searchForm.getTagId() != null) {
            params.put("tagId", searchForm.getTagId());
        }
        if (searchForm.getMinPrice() != null) {
            params.put("minPrice", searchForm.getMinPrice());
        }
        if (searchForm.getMaxPrice() != null) {
            params.put("maxPrice", searchForm.getMaxPrice());
        }
        if (searchForm.getPubDateStart() != null) {
            params.put("pubDateStart", searchForm.getPubDateStart());
        }
        if (searchForm.getPubDateEnd() != null) {
            params.put("pubDateEnd", searchForm.getPubDateEnd());
        }
        if (searchForm.getMinSourceScore() != null) {
            params.put("minSourceScore", searchForm.getMinSourceScore());
        }
        if (searchForm.getMinSentimentScore() != null) {
            params.put("minSentimentScore", searchForm.getMinSentimentScore());
        }

        return params;
    }

    /**
     * 组装 BookBO
     */
    private BookBO assembleBookBO(Book book, Category category, List<Map<String, Object>> tags) {
        BookBO bo = BookBO.builder()
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

        // 设置分类信息
        if (category != null) {
            bo.setCategoryId(category.getCategoryId());
            bo.setCategoryName(category.getName());
        }

        // 设置标签列表
        if (tags != null && !tags.isEmpty()) {
            List<BookBO.TagSimpleDTO> tagDTOs = tags.stream()
                    .map(tag -> BookBO.TagSimpleDTO.builder()
                            .tagId((String) tag.get("tagId"))
                            .name((String) tag.get("tagName"))
                            .type((Integer) tag.get("tagType"))
                            .build())
                    .collect(Collectors.toList());
            bo.setTags(tagDTOs);
        }

        return bo;
    }
}
