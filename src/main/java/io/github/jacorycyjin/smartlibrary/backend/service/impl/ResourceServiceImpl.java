package io.github.jacorycyjin.smartlibrary.backend.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.jacorycyjin.smartlibrary.backend.common.dto.PageDTO;
import io.github.jacorycyjin.smartlibrary.backend.common.dto.PageQueryDTO;
import io.github.jacorycyjin.smartlibrary.backend.common.enums.ApiCode;
import io.github.jacorycyjin.smartlibrary.backend.common.exception.BusinessException;
import io.github.jacorycyjin.smartlibrary.backend.dto.CategoryDTO;
import io.github.jacorycyjin.smartlibrary.backend.dto.ResourceDTO;
import io.github.jacorycyjin.smartlibrary.backend.dto.TagDTO;
import io.github.jacorycyjin.smartlibrary.backend.entity.Category;
import io.github.jacorycyjin.smartlibrary.backend.entity.Resource;
import io.github.jacorycyjin.smartlibrary.backend.form.ResourceSearchForm;
import io.github.jacorycyjin.smartlibrary.backend.mapper.CategoryMapper;
import io.github.jacorycyjin.smartlibrary.backend.mapper.ResourceMapper;
import io.github.jacorycyjin.smartlibrary.backend.mapper.TagMapper;
import io.github.jacorycyjin.smartlibrary.backend.service.ResourceService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源服务实现
 * 
 * @author Jacory
 * @date 2025/01/19
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @jakarta.annotation.Resource
    private ResourceMapper resourceMapper;

    @jakarta.annotation.Resource
    private CategoryMapper categoryMapper;

    @jakarta.annotation.Resource
    private TagMapper tagMapper;

    @Override
    public PageDTO<ResourceDTO> searchResources(ResourceSearchForm searchForm) {
        // 校验并设置分页参数默认值（必须使用返回的 DTO）
        PageQueryDTO pageQuery = searchForm.toPageQueryDTO();
        
        // 构建查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("type", searchForm.getType());
        params.put("keyword", searchForm.getKeyword());
        
        // 展开分类ID：如果传入的是父分类，需要查询所有子分类
        List<String> expandedCategoryIds = expandCategoryIds(searchForm.getCategoryIds());
        params.put("categoryIds", expandedCategoryIds);
        
        params.put("tagIds", searchForm.getTagIds());
        params.put("authorName", searchForm.getAuthorName());
        params.put("publisher", searchForm.getPublisher());
        params.put("journal", searchForm.getJournal());
        params.put("sortBy", searchForm.getSortBy());

        // 分页查询（使用校验后的分页参数）
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        List<Resource> resources = resourceMapper.searchResources(params);
        PageInfo<Resource> pageInfo = new PageInfo<>(resources);

        // 转换为 DTO
        List<ResourceDTO> resourceDTOs = resources.stream()
                .map(this::convertToDTO)
                .toList();

        return new PageDTO<>(
                pageQuery.getPageNum(),
                (int) pageInfo.getTotal(),
                pageQuery.getPageSize(),
                resourceDTOs
        );
    }

    /**
     * 展开分类ID：将父分类展开为包含所有子分类的ID列表
     * 
     * @param categoryIds 原始分类ID列表
     * @return 展开后的分类ID列表（包含所有子分类）
     */
    private List<String> expandCategoryIds(List<String> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return null;
        }
        
        // 对每个分类ID，查询其所有子分类
        return categoryIds.stream()
                .flatMap(categoryId -> categoryMapper.selectDescendantIds(categoryId).stream())
                .distinct()
                .toList();
    }

    @Override
    public ResourceDTO getResourceDetail(String resourceId) {
        // 查询资源基本信息
        Resource resource = resourceMapper.selectByResourceId(resourceId);
        if (resource == null) {
            throw new BusinessException(ApiCode.RESOURCE_NOT_FOUND.getCode(), ApiCode.RESOURCE_NOT_FOUND.getMessage());
        }

        // 转换为 DTO（包含完整分类层级和标签）
        return convertToDetailDTO(resource);
    }

    @Override
    public void incrementViewCount(String resourceId) {
        resourceMapper.incrementViewCount(resourceId);
    }

    /**
     * 转换为基础 DTO（用于列表页）
     */
    private ResourceDTO convertToDTO(Resource resource) {
        // 查询分类（直接关联的分类，通常是最子分类）
        List<Category> categories = categoryMapper.selectCategoriesByResourceId(resource.getResourceId());
        
        // 只保留 level 最大的分类（最子分类）
        List<CategoryDTO> categoryDTOs = categories.stream()
                .filter(c -> c.getLevel() != null)
                .sorted((c1, c2) -> Integer.compare(c2.getLevel(), c1.getLevel())) // 降序
                .limit(1) // 只取最子分类
                .map(CategoryDTO::fromEntity)
                .toList();

        // 查询标签
        List<Map<String, Object>> tagMaps = tagMapper.selectTagsByResourceIds(List.of(resource.getResourceId()));
        List<TagDTO> tags = tagMaps.stream()
                .map(TagDTO::fromMap)
                .toList();

        // 转换为 DTO
        ResourceDTO dto = ResourceDTO.fromEntity(resource);
        dto.setCategories(categoryDTOs);
        dto.setTags(tags);
        
        return dto;
    }

    /**
     * 转换为详情 DTO（用于详情页，包含完整分类层级）
     */
    private ResourceDTO convertToDetailDTO(Resource resource) {
        // 查询完整分类层级
        List<Category> categoryPaths = categoryMapper.selectCategoryPathsByResourceId(resource.getResourceId());
        List<CategoryDTO> categoryDTOs = categoryPaths.stream()
                .map(CategoryDTO::fromEntity)
                .toList();

        // 查询所有标签
        List<Map<String, Object>> tagMaps = tagMapper.selectTagsByResourceIds(List.of(resource.getResourceId()));
        List<TagDTO> tags = tagMaps.stream()
                .map(TagDTO::fromMap)
                .toList();

        // 转换为 DTO
        ResourceDTO dto = ResourceDTO.fromEntity(resource);
        dto.setCategories(categoryDTOs);
        dto.setTags(tags);
        
        return dto;
    }
}
