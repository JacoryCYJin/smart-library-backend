package io.github.jacorycyjin.smartlibrary.backend.mapper;

import io.github.jacorycyjin.smartlibrary.backend.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 分类 Mapper
 * 
 * @author Jacory
 * @date 2025/12/31
 */
@Mapper
public interface CategoryMapper {

    /**
     * 根据业务ID列表批量查询分类
     * 
     * @param categoryIds 分类业务ID列表（对应 category 表的 category_id 字段）
     * @return 分类列表
     */
    List<Category> selectByCategoryIds(@Param("categoryIds") List<String> categoryIds);

    /**
     * 根据分类业务ID查询单个分类
     * 
     * @param categoryId 分类业务ID
     * @return 分类信息
     */
    Category selectByCategoryId(@Param("categoryId") String categoryId);

    /**
     * 根据资源ID查询关联的分类（仅一级分类）
     * 
     * @param resourceId 资源业务ID
     * @return 分类实体列表
     */
    List<Category> selectCategoriesByResourceId(@Param("resourceId") String resourceId);

    /**
     * 根据资源ID查询完整分类层级路径
     * 
     * @param resourceId 资源业务ID
     * @return 分类实体列表（包含所有祖先节点）
     */
    List<Category> selectCategoryPathsByResourceId(@Param("resourceId") String resourceId);

    /**
     * 查询所有分类
     * 
     * @return 分类列表
     */
    List<Category> selectAll();
}
