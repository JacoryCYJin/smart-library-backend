package io.github.jacorycyjin.smartlibrary.backend.service;

import io.github.jacorycyjin.smartlibrary.backend.dto.CategoryDTO;

import java.util.List;

/**
 * 分类服务接口
 * 
 * @author Jacory
 * @date 2025/01/20
 */
public interface CategoryService {

    /**
     * 获取所有分类列表
     * 
     * @return 分类DTO列表
     */
    List<CategoryDTO> getAllCategories();
}
