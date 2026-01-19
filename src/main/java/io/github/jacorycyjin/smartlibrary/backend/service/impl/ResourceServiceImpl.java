package io.github.jacorycyjin.smartlibrary.backend.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.jacorycyjin.smartlibrary.backend.common.dto.PageDTO;
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
        // 构建查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("type", searchForm.getType());
        params.put("keyword", searchForm.getKeyword());
        params.put("categoryIds", searchForm.getCategoryIds());
        params.put("tagIds", searchForm.getTagIds());
        params.put("authorName", searchForm.getAuthorName());
        params.put("publisher", searchForm.getPublisher());
        params.put("journal", searchForm.getJournal());
        params.put("sortBy", searchForm.getSortBy());
        params.put("sortOrder", searchForm.getSortOrder());

        // 分页查询
        PageHelper.startPage(searchForm.getPageNum(), searchForm.getPageSize());
        List<Resource> resources = resourceMapper.searchResources(params);
        PageInfo<Resource> pageInfo = new PageInfo<>(resources);

        // 转换为 DTO
        List<ResourceDTO> resourceDTOs = resources.stream()
                .map(this::convertToDTO)
                .toList();

        return new PageDTO<>(
                searchForm.getPageNum(),
                (int) pageInfo.getTotal(),
                searchForm.getPageSize(),
                resourceDTOs
        );
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
        // 查询分类（仅一级分类）
        List<Category> categories = categoryMapper.selectCategoriesByResourceId(resource.getResourceId());
        List<CategoryDTO> categoryDTOs = categories.stream()
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
