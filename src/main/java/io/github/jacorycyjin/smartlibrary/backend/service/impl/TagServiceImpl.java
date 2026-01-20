package io.github.jacorycyjin.smartlibrary.backend.service.impl;

import io.github.jacorycyjin.smartlibrary.backend.dto.TagDTO;
import io.github.jacorycyjin.smartlibrary.backend.mapper.TagMapper;
import io.github.jacorycyjin.smartlibrary.backend.service.TagService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 标签服务实现
 * 
 * @author Jacory
 * @date 2025/01/20
 */
@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Override
    public List<TagDTO> getAllTags() {
        List<Map<String, Object>> tags = tagMapper.selectAll();
        
        return tags.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Map 转 DTO
     */
    private TagDTO convertToDTO(Map<String, Object> map) {
        return TagDTO.builder()
                .tagId((String) map.get("tagId"))
                .name((String) map.get("name"))
                .type((Integer) map.get("type"))
                .ctime(map.get("ctime") != null ? ((Timestamp) map.get("ctime")).toLocalDateTime() : null)
                .mtime(map.get("mtime") != null ? ((Timestamp) map.get("mtime")).toLocalDateTime() : null)
                .build();
    }
}
