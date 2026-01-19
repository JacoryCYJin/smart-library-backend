package io.github.jacorycyjin.smartlibrary.backend.mapper;

import io.github.jacorycyjin.smartlibrary.backend.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 资源 Mapper
 * 
 * @author Jacory
 * @date 2025/01/19
 */
@Mapper
public interface ResourceMapper {

    /**
     * 根据资源ID查询
     * 
     * @param resourceId 资源业务ID
     * @return 资源实体
     */
    Resource selectByResourceId(@Param("resourceId") String resourceId);

    /**
     * 多条件搜索资源（支持分页）
     * 
     * @param params 查询参数
     * @return 资源列表
     */
    List<Resource> searchResources(@Param("params") Map<String, Object> params);

    /**
     * 统计搜索结果数量
     * 
     * @param params 查询参数
     * @return 总数
     */
    int countResources(@Param("params") Map<String, Object> params);

    /**
     * 插入资源
     * 
     * @param resource 资源实体
     * @return 影响行数
     */
    int insert(Resource resource);

    /**
     * 更新资源
     * 
     * @param resource 资源实体
     * @return 影响行数
     */
    int update(Resource resource);

    /**
     * 增加浏览量
     * 
     * @param resourceId 资源ID
     * @return 影响行数
     */
    int incrementViewCount(@Param("resourceId") String resourceId);

    /**
     * 增加评论数
     * 
     * @param resourceId 资源ID
     * @return 影响行数
     */
    int incrementCommentCount(@Param("resourceId") String resourceId);

    /**
     * 减少评论数
     * 
     * @param resourceId 资源ID
     * @return 影响行数
     */
    int decrementCommentCount(@Param("resourceId") String resourceId);

    /**
     * 增加收藏数
     * 
     * @param resourceId 资源ID
     * @return 影响行数
     */
    int incrementStarCount(@Param("resourceId") String resourceId);

    /**
     * 减少收藏数
     * 
     * @param resourceId 资源ID
     * @return 影响行数
     */
    int decrementStarCount(@Param("resourceId") String resourceId);
}
