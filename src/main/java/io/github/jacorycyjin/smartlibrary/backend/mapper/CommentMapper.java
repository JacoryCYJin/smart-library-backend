package io.github.jacorycyjin.smartlibrary.backend.mapper;

import io.github.jacorycyjin.smartlibrary.backend.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 评论 Mapper
 * 
 * @author Jacory
 * @date 2025/01/19
 */
@Mapper
public interface CommentMapper {

    /**
     * 根据资源ID查询已通过审核的评论列表（支持分页）
     * 
     * @param resourceId 资源业务ID
     * @return 评论列表（按创建时间倒序）
     */
    List<Comment> selectByResourceId(@Param("resourceId") String resourceId);

    /**
     * 统计资源的评论总数
     * 
     * @param resourceId 资源业务ID
     * @return 评论总数
     */
    int countByResourceId(@Param("resourceId") String resourceId);
}
