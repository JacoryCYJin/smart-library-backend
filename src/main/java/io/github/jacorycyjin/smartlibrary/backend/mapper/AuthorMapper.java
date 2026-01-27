package io.github.jacorycyjin.smartlibrary.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 作者 Mapper
 * 
 * @author Jacory
 * @date 2025/01/27
 */
@Mapper
public interface AuthorMapper {

    /**
     * 根据资源ID查询作者列表（按 sort 排序）
     * 
     * @param resourceId 资源ID
     * @return 作者信息列表（包含 sort 字段）
     */
    List<Map<String, Object>> selectAuthorsByResourceId(@Param("resourceId") String resourceId);

    /**
     * 批量查询资源的作者（按 sort 排序）
     * 
     * @param resourceIds 资源ID列表
     * @return 作者信息列表（包含 resourceId 和 sort 字段）
     */
    List<Map<String, Object>> selectAuthorsByResourceIds(@Param("resourceIds") List<String> resourceIds);
}
