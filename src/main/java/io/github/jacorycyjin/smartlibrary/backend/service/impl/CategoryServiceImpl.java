package io.github.jacorycyjin.smartlibrary.backend.service.impl;

import io.github.jacorycyjin.smartlibrary.backend.dto.CategoryDTO;
import io.github.jacorycyjin.smartlibrary.backend.entity.Category;
import io.github.jacorycyjin.smartlibrary.backend.mapper.CategoryMapper;
import io.github.jacorycyjin.smartlibrary.backend.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类服务实现
 * 
 * @author Jacory
 * @date 2025/01/20
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryMapper.selectAll();
        
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Entity 转 DTO
     */
    private CategoryDTO convertToDTO(Category entity) {
        return CategoryDTO.builder()
                .categoryId(entity.getCategoryId())
                .parentCategoryId(entity.getParentCategoryId())
                .name(entity.getName())
                .level(entity.getLevel())
                .sortOrder(entity.getSortOrder())
                .ctime(entity.getCtime())
                .mtime(entity.getMtime())
                .build();
    }
}
