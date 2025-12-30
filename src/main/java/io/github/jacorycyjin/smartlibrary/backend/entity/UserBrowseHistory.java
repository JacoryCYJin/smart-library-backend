package io.github.jacorycyjin.smartlibrary.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

/**
 * 用户浏览历史实体类
 * 
 * @author Jacory
 * @date 2025/12/28
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class UserBrowseHistory {

    /**
     * 主键ID，自增
     */
    private Long id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 图书ID
     */
    private String bookId;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 创建时间
     */
    private LocalDateTime ctime;

    /**
     * 更新时间（最后浏览时间）
     */
    private LocalDateTime mtime;

    /**
     * 是否删除：0=未删除，1=已删除
     */
    private Integer deleted;
}
