package io.github.jacorycyjin.smartlibrary.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

/**
 * 图书-作者关联实体类
 * 
 * @author Jacory
 * @date 2025/12/28
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class BookAuthorRel {

    /**
     * 主键ID，自增
     */
    private Long id;

    /**
     * 图书业务ID
     */
    private String bookId;

    /**
     * 作者业务ID
     */
    private String authorId;

    /**
     * 角色（作者、译者等）
     */
    private String role;

    /**
     * 排序权重
     */
    private Integer sortOrder;

    /**
     * 创建时间
     */
    private LocalDateTime ctime;

    /**
     * 更新时间
     */
    private LocalDateTime mtime;

    /**
     * 是否删除：0=未删除，1=已删除
     */
    private Integer deleted;
}
