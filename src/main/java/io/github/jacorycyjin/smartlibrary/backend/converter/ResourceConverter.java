package io.github.jacorycyjin.smartlibrary.backend.converter;

import io.github.jacorycyjin.smartlibrary.backend.dto.CategoryDTO;
import io.github.jacorycyjin.smartlibrary.backend.dto.ResourceDTO;
import io.github.jacorycyjin.smartlibrary.backend.vo.CategoryVO;
import io.github.jacorycyjin.smartlibrary.backend.vo.ResourceDetailVO;
import io.github.jacorycyjin.smartlibrary.backend.vo.ResourcePublicVO;
import io.github.jacorycyjin.smartlibrary.backend.vo.TagVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源转换器（处理复杂的 DTO -> VO 转换）
 * 
 * @author Jacory
 * @date 2025/01/19
 */
public class ResourceConverter {

    /**
     * 将 ResourceDTO 转换为 ResourceDetailVO（包含完整分类层级）
     * 
     * @param dto 资源 DTO
     * @return 资源详情 VO
     */
    public static ResourceDetailVO toDetailVO(ResourceDTO dto) {
        if (dto == null) {
            return null;
        }

        // 先转换为 PublicVO（复用基础字段转换逻辑）
        ResourcePublicVO publicVO = ResourcePublicVO.fromDTO(dto);

        // 构建完整分类层级路径
        List<List<CategoryVO>> categoryPaths = buildCategoryPaths(dto.getCategories());

        // 转换标签
        List<TagVO> tags = dto.getTags() != null
                ? dto.getTags().stream()
                .map(TagVO::fromDTO)
                .toList()
                : null;

        // 构建 DetailVO
        return ResourceDetailVO.builder()
                // 继承 PublicVO 的字段
                .resourceId(publicVO.getResourceId())
                .type(publicVO.getType())
                .title(publicVO.getTitle())
                .subTitle(publicVO.getSubTitle())
                .authorName(publicVO.getAuthorName())
                .coverUrl(publicVO.getCoverUrl())
                .pubDate(publicVO.getPubDate())
                .publisher(publicVO.getPublisher())
                .journal(publicVO.getJournal())
                .sourceScore(publicVO.getSourceScore())
                .viewCount(publicVO.getViewCount())
                .commentCount(publicVO.getCommentCount())
                .starCount(publicVO.getStarCount())
                .tags(tags)
                // DetailVO 特有字段
                .isbn(dto.getIsbn())
                .price(dto.getPrice())
                .pageCount(dto.getPageCount())
                .doi(dto.getDoi())
                .fileUrl(dto.getFileUrl())
                .fileType(dto.getFileType())
                .summary(dto.getSummary())
                .sourceOrigin(dto.getSourceOrigin())
                .sourceUrl(dto.getSourceUrl())
                .sentimentScore(dto.getSentimentScore())
                .categoryPaths(categoryPaths)
                .ctime(dto.getCtime())
                .mtime(dto.getMtime())
                .build();
    }

    /**
     * 构建分类层级路径
     * 
     * 输入: [Java(id=3, pid=2), 编程语言(id=2, pid=1), 计算机(id=1, pid=null)]
     * 输出: [[计算机, 编程语言, Java]]
     * 
     * @param categories 扁平化的分类列表
     * @return 分类层级路径列表
     */
    private static List<List<CategoryVO>> buildCategoryPaths(List<CategoryDTO> categories) {
        if (categories == null || categories.isEmpty()) {
            return new ArrayList<>();
        }

        // 按 categoryId 分组（同一个 categoryId 的所有祖先节点）
        Map<String, List<CategoryDTO>> groupedByLeaf = new HashMap<>();
        
        for (CategoryDTO category : categories) {
            // 找到叶子节点（最深层的分类）
            String leafId = findLeafCategoryId(category, categories);
            groupedByLeaf.computeIfAbsent(leafId, k -> new ArrayList<>()).add(category);
        }

        // 为每个叶子节点构建完整路径
        List<List<CategoryVO>> paths = new ArrayList<>();
        for (Map.Entry<String, List<CategoryDTO>> entry : groupedByLeaf.entrySet()) {
            List<CategoryDTO> pathCategories = entry.getValue();
            
            // 按层级排序（从根到叶子）
            pathCategories.sort((a, b) -> {
                int levelA = calculateLevel(a, pathCategories);
                int levelB = calculateLevel(b, pathCategories);
                return Integer.compare(levelA, levelB);
            });

            // 转换为 VO
            List<CategoryVO> path = pathCategories.stream()
                    .map(CategoryVO::fromDTO)
                    .toList();
            
            paths.add(path);
        }

        return paths;
    }

    /**
     * 找到叶子节点ID（没有子节点的分类）
     */
    private static String findLeafCategoryId(CategoryDTO category, List<CategoryDTO> allCategories) {
        // 检查是否有其他分类的 parentId 指向当前分类
        boolean hasChildren = allCategories.stream()
                .anyMatch(c -> category.getCategoryId().equals(c.getParentId()));
        
        if (hasChildren) {
            // 如果有子节点，继续查找
            CategoryDTO child = allCategories.stream()
                    .filter(c -> category.getCategoryId().equals(c.getParentId()))
                    .findFirst()
                    .orElse(null);
            return child != null ? findLeafCategoryId(child, allCategories) : category.getCategoryId();
        } else {
            // 没有子节点，就是叶子节点
            return category.getCategoryId();
        }
    }

    /**
     * 计算分类的层级（根节点为0）
     */
    private static int calculateLevel(CategoryDTO category, List<CategoryDTO> allCategories) {
        if (category.getParentId() == null) {
            return 0;
        }
        
        CategoryDTO parent = allCategories.stream()
                .filter(c -> c.getCategoryId().equals(category.getParentId()))
                .findFirst()
                .orElse(null);
        
        return parent != null ? calculateLevel(parent, allCategories) + 1 : 0;
    }
}
