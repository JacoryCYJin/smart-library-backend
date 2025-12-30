package io.github.jacorycyjin.smartlibrary.backend.dto;

import io.github.jacorycyjin.smartlibrary.backend.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 图书标签 DTO
 * 
 * @author Jacory
 * @date 2025/12/28
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class TagDTO {

    /**
     * 标签业务ID
     */
    private String tagId;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签类型：0=NLP，1=人工，2=爬虫
     */
    private Integer type;

    /**
     * 创建时间
     */
    private LocalDateTime ctime;

    /**
     * 更新时间
     */
    private LocalDateTime mtime;

    /**
     * 从实体类转换为 DTO
     */
    public static TagDTO fromEntity(Tag tag) {
        if (tag == null) {
            return null;
        }
        return TagDTO.builder()
                .tagId(tag.getTagId())
                .name(tag.getName())
                .type(tag.getType())
                .ctime(tag.getCtime())
                .mtime(tag.getMtime())
                .build();
    }
}
