package io.github.jacorycyjin.smartlibrary.backend.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 极简标签 VO (嵌入在 BookPublicVO 中使用)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagSimpleVO {
    private String tagId;
    private String name;
    
    /**
     * 标签类型：0=NLP(蓝), 1=人工(红), 2=爬虫(灰)
     * 前端根据这个字段决定渲染什么颜色的气泡
     */
    private Integer type;

    /**
     * 从 Map 转换为 VO（用于 MyBatis 查询结果）
     */
    public static TagSimpleVO fromMap(java.util.Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        return TagSimpleVO.builder()
                .tagId((String) map.get("tagId"))
                .name((String) map.get("tagName"))
                .type((Integer) map.get("tagType"))
                .build();
    }
}