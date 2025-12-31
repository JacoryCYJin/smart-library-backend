package io.github.jacorycyjin.smartlibrary.backend.converter;

import io.github.jacorycyjin.smartlibrary.backend.entity.Book;
import io.github.jacorycyjin.smartlibrary.backend.entity.Category;
import io.github.jacorycyjin.smartlibrary.backend.entity.Comment;
import io.github.jacorycyjin.smartlibrary.backend.entity.User;
import io.github.jacorycyjin.smartlibrary.backend.bo.BookBO;
import io.github.jacorycyjin.smartlibrary.backend.vo.BookDetailVO;
import io.github.jacorycyjin.smartlibrary.backend.vo.CategoryVO;
import io.github.jacorycyjin.smartlibrary.backend.vo.TagSimpleVO;
import io.github.jacorycyjin.smartlibrary.backend.vo.CommentVO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 图书转换器（处理复杂的转换逻辑）
 * 
 * @author Jacory
 * @date 2025/12/31
 */
public class BookConverter {

    /**
     * 组装 BookBO（Book + Category + Tags）
     * 
     * @param book 图书实体
     * @param category 分类实体
     * @param tagMaps 标签 Map 列表
     * @return BookBO
     */
    public static BookBO toBookBO(Book book, Category category, List<Map<String, Object>> tagMaps) {
        if (book == null) {
            return null;
        }

        BookBO.BookBOBuilder builder = BookBO.builder()
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
                .mtime(book.getMtime());

        // 设置分类信息
        if (category != null) {
            builder.categoryId(category.getCategoryId())
                   .categoryName(category.getName());
        }

        // 设置标签列表
        if (tagMaps != null && !tagMaps.isEmpty()) {
            List<BookBO.TagSimpleDTO> tagDTOs = tagMaps.stream()
                    .map(tag -> BookBO.TagSimpleDTO.builder()
                            .tagId((String) tag.get("tagId"))
                            .name((String) tag.get("tagName"))
                            .type((Integer) tag.get("tagType"))
                            .build())
                    .collect(Collectors.toList());
            builder.tags(tagDTOs);
        }

        return builder.build();
    }

    /**
     * 组装 BookDetailVO（Book + CategoryPath + AllTags）
     * 
     * @param book 图书实体
     * @param categoryPath 分类层级路径
     * @param tagMaps 标签 Map 列表
     * @return BookDetailVO
     */
    public static BookDetailVO toBookDetailVO(Book book, List<CategoryVO> categoryPath, List<Map<String, Object>> tagMaps) {
        if (book == null) {
            return null;
        }

        // 转换所有标签
        List<TagSimpleVO> allTags = tagMaps != null ? 
                tagMaps.stream()
                        .map(TagSimpleVO::fromMap)
                        .collect(Collectors.toList()) : 
                List.of();

        BookDetailVO detailVO = BookDetailVO.builder()
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
                .categoryPath(categoryPath)
                .allTags(allTags)
                .build();

        // 设置父类字段（用于兼容）
        if (categoryPath != null && !categoryPath.isEmpty()) {
            CategoryVO leafCategory = categoryPath.get(categoryPath.size() - 1);
            detailVO.setCategoryId(leafCategory.getCategoryId());
            detailVO.setCategoryName(leafCategory.getName());
        }
        
        // 设置前3个标签（用于兼容父类）
        if (!allTags.isEmpty()) {
            detailVO.setTags(allTags.stream().limit(3).collect(Collectors.toList()));
        }

        return detailVO;
    }

    /**
     * 批量转换评论（Comment + User -> CommentVO）
     * 
     * @param comments 评论列表
     * @param userMap 用户 Map（userId -> User）
     * @return CommentVO 列表
     */
    public static List<CommentVO> toCommentVOList(List<Comment> comments, Map<String, User> userMap) {
        if (comments == null || comments.isEmpty()) {
            return List.of();
        }

        return comments.stream()
                .map(comment -> {
                    User user = userMap.get(comment.getUserId());
                    return CommentVO.fromEntityWithUser(comment, user);
                })
                .collect(Collectors.toList());
    }

    /**
     * 构建分类层级路径（从叶子到根，然后反转）
     * 
     * @param leafCategory 叶子分类
     * @param categoryFetcher 分类获取函数（根据 categoryId 获取 Category）
     * @return 分类路径列表（从根到叶子）
     */
    public static List<CategoryVO> buildCategoryPath(Category leafCategory, 
                                                      java.util.function.Function<String, Category> categoryFetcher) {
        List<CategoryVO> path = new java.util.ArrayList<>();
        Category currentCategory = leafCategory;

        // 从叶子节点向上遍历到根节点
        while (currentCategory != null) {
            // 插入到列表头部，保证顺序是从根到叶子
            path.add(0, CategoryVO.fromEntity(currentCategory));

            // 继续向上查找父分类
            String parentId = currentCategory.getParentCategoryId();
            if (parentId == null) {
                break;
            }
            currentCategory = categoryFetcher.apply(parentId);
        }

        return path;
    }
}
