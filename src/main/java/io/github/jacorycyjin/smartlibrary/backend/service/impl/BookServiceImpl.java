package io.github.jacorycyjin.smartlibrary.backend.service.impl;

import io.github.jacorycyjin.smartlibrary.backend.service.BookService;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import io.github.jacorycyjin.smartlibrary.backend.mapper.BookMapper;
import io.github.jacorycyjin.smartlibrary.backend.mapper.CategoryMapper;
import io.github.jacorycyjin.smartlibrary.backend.mapper.TagMapper;
import io.github.jacorycyjin.smartlibrary.backend.mapper.BookCategoryRelMapper;
import io.github.jacorycyjin.smartlibrary.backend.mapper.CommentMapper;
import io.github.jacorycyjin.smartlibrary.backend.mapper.UserMapper;
import io.github.jacorycyjin.smartlibrary.backend.entity.Book;
import io.github.jacorycyjin.smartlibrary.backend.entity.Category;
import io.github.jacorycyjin.smartlibrary.backend.entity.Comment;
import io.github.jacorycyjin.smartlibrary.backend.entity.User;
import io.github.jacorycyjin.smartlibrary.backend.bo.BookBO;
import io.github.jacorycyjin.smartlibrary.backend.form.BookSearchForm;
import io.github.jacorycyjin.smartlibrary.backend.common.dto.PageDTO;
import io.github.jacorycyjin.smartlibrary.backend.common.dto.PageQueryDTO;
import io.github.jacorycyjin.smartlibrary.backend.vo.BookDetailVO;
import io.github.jacorycyjin.smartlibrary.backend.vo.CategoryVO;
import io.github.jacorycyjin.smartlibrary.backend.vo.CommentVO;
import io.github.jacorycyjin.smartlibrary.backend.common.exception.BusinessException;
import io.github.jacorycyjin.smartlibrary.backend.common.enums.ApiCode;
import io.github.jacorycyjin.smartlibrary.backend.converter.BookConverter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * 图书服务实现
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

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private UserMapper userMapper;

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

        // 6. 批量查询图书-分类关联
        List<Map<String, Object>> bookCategoryRelList = bookCategoryRelMapper.selectByBookIds(bookIds);

        // 提取分类业务ID列表
        List<String> categoryIds = bookCategoryRelList.stream()
                .map(rel -> (String) rel.get("categoryId"))
                .distinct()
                .collect(Collectors.toList());

        // 7. 批量查询分类信息
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

        // 9. 批量查询标签信息
        List<Map<String, Object>> tagList = tagMapper.selectTagsByBookIds(bookIds);
        Map<String, List<Map<String, Object>>> tagMap = tagList.stream()
                .collect(Collectors.groupingBy(m -> (String) m.get("bookId")));

        // 10. 组装数据：Book + Category + Tags -> BookBO（使用转换器）
        List<BookBO> bookBOs = books.stream()
                .map(book -> BookConverter.toBookBO(
                        book,
                        bookToCategoryMap.get(book.getBookId()),
                        tagMap.get(book.getBookId())))
                .collect(Collectors.toList());

        // 11. 返回分页结果
        return new PageDTO<>(pageQuery.getPageNum(), totalCount, pageQuery.getPageSize(), bookBOs);
    }

    @Override
    public BookDetailVO getBookDetail(String bookId) {
        // 1. 查询图书基本信息
        Book book = bookMapper.selectByBookId(bookId);
        if (book == null) {
            throw new BusinessException(ApiCode.BOOK_NOT_FOUND.getCode(), ApiCode.BOOK_NOT_FOUND.getMessage());
        }

        // 2. 查询图书的分类ID
        List<Map<String, Object>> bookCategoryRelList = bookCategoryRelMapper.selectByBookIds(List.of(bookId));
        String categoryId = null;
        if (!bookCategoryRelList.isEmpty()) {
            categoryId = (String) bookCategoryRelList.get(0).get("categoryId");
        }

        // 3. 构建分类层级路径（从根到叶子）
        List<CategoryVO> categoryPath = new ArrayList<>();
        if (categoryId != null) {
            Category leafCategory = categoryMapper.selectByCategoryId(categoryId);
            if (leafCategory != null) {
                categoryPath = BookConverter.buildCategoryPath(leafCategory, categoryMapper::selectByCategoryId);
            }
        }

        // 4. 查询所有标签
        List<Map<String, Object>> tagList = tagMapper.selectTagsByBookIds(List.of(bookId));

        // 5. 组装 BookDetailVO（使用转换器）
        return BookConverter.toBookDetailVO(book, categoryPath, tagList);
    }

    @Override
    public PageDTO<CommentVO> getBookComments(String bookId, Integer pageNum, Integer pageSize) {
        // 1. 设置默认分页参数
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        // 2. 启动分页查询评论
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> comments = commentMapper.selectByBookId(bookId);
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);

        if (comments.isEmpty()) {
            return new PageDTO<>(pageNum, 0, pageSize, new ArrayList<>());
        }

        // 3. 提取用户ID列表
        List<String> userIds = comments.stream()
                .map(Comment::getUserId)
                .distinct()
                .collect(Collectors.toList());

        // 4. 批量查询用户信息
        List<User> users = userMapper.selectByUserIds(userIds);
        Map<String, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getUserId, u -> u));

        // 5. 组装 CommentVO（使用转换器）
        List<CommentVO> commentVOs = BookConverter.toCommentVOList(comments, userMap);

        // 6. 返回分页结果
        return new PageDTO<>(pageNum, (int) pageInfo.getTotal(), pageSize, commentVOs);
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
}
