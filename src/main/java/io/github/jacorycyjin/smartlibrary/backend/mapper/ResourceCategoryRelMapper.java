package io.github.jacorycyjin.smartlibrary.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 资源-分类关联 Mapper
 * 
 * @author Jacory
 * @date 2025/01/19
 */
@Mapper
public interface ResourceCategoryRelMapper {

    /**
     * 根据资源ID列表批量查询分类关联信息
     * 
     * @param resourceIds 资源业务ID列表
     * @return 关联信息列表（包含 resourceId, categoryId）
     */
    List<Map<String, Object>> selectByResourceIds(@Param("resourceIds") List<String> resourceIds);
}
