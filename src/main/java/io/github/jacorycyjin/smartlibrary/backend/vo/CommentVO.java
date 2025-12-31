package io.github.jacorycyjin.smartlibrary.backend.vo;

import io.github.jacorycyjin.smartlibrary.backend.dto.CommentDTO;
import io.github.jacorycyjin.smartlibrary.backend.entity.Comment;
import io.github.jacorycyjin.smartlibrary.backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 图书评论与评分 VO
 * 
 * @author Jacory
 * @date 2025/12/28
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class CommentVO {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 用户头像URL
     */
    private String avatarUrl;

    /**
     * 图书ID
     */
    private String bookId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评分（1-5）
     */
    private BigDecimal score;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 审核状态：0=待审，1=通过，2=拒绝
     */
    private Integer auditStatus;

    /**
     * 拒绝理由
     */
    private String rejectionReason;

    /**
     * 创建时间
     */
    private LocalDateTime ctime;

    /**
     * 更新时间
     */
    private LocalDateTime mtime;

    /**
     * 从 DTO 转换为 VO
     */
    public static CommentVO fromDTO(CommentDTO dto) {
        if (dto == null) {
            return null;
        }
        return CommentVO.builder()
                .userId(dto.getUserId())
                .bookId(dto.getBookId())
                .content(dto.getContent())
                .score(dto.getScore())
                .likeCount(dto.getLikeCount())
                .auditStatus(dto.getAuditStatus())
                .rejectionReason(dto.getRejectionReason())
                .ctime(dto.getCtime())
                .mtime(dto.getMtime())
                .build();
    }

    /**
     * 从实体类转换为 VO（不包含用户信息）
     */
    public static CommentVO fromEntity(Comment entity) {
        if (entity == null) {
            return null;
        }
        return CommentVO.builder()
                .userId(entity.getUserId())
                .bookId(entity.getBookId())
                .content(entity.getContent())
                .score(entity.getScore())
                .likeCount(entity.getLikeCount())
                .auditStatus(entity.getAuditStatus())
                .rejectionReason(entity.getRejectionReason())
                .ctime(entity.getCtime())
                .mtime(entity.getMtime())
                .build();
    }

    /**
     * 从实体类转换为 VO（包含用户信息）
     */
    public static CommentVO fromEntityWithUser(Comment comment, User user) {
        if (comment == null) {
            return null;
        }
        return CommentVO.builder()
                .userId(comment.getUserId())
                .username(user != null ? user.getUsername() : "未知用户")
                .avatarUrl(user != null ? user.getAvatarUrl() : null)
                .bookId(comment.getBookId())
                .content(comment.getContent())
                .score(comment.getScore())
                .likeCount(comment.getLikeCount())
                .auditStatus(comment.getAuditStatus())
                .rejectionReason(comment.getRejectionReason())
                .ctime(comment.getCtime())
                .mtime(comment.getMtime())
                .build();
    }
}
