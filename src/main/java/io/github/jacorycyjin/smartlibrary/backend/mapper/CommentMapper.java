package io.github.jacorycyjin.smartlibrary.backend.mapper;

import io.github.jacorycyjin.smartlibrary.backend.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 评论 Mapper
 * 
 * @author Jacory
 * @date 2025/12/31
 */
@Mapper
public interface CommentMapper {

    /**
     * 根据图书ID查询已通过审核的评论列表（支持分页）
     * 
     * @param bookId 图书业务ID
     * @return 评论列表（按创建时间倒序）
     */
    List<Comment> selectByBookId(@Param("bookId") String bookId);

    /**
     * 统计图书的评论总数
     * 
     * @param bookId 图书业务ID
     * @return 评论总数
     */
    int countByBookId(@Param("bookId") String bookId);
}
