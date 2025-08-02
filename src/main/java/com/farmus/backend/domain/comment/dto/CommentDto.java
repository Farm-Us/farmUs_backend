package com.farmus.backend.domain.comment.dto;

import com.farmus.backend.domain.comment.entity.Comment;

import java.time.LocalDateTime;

public record CommentDto(
        Long id,
        String content,
        Long userId,
        String username,
        Long postId,
        Long parentCommentId,
        LocalDateTime createdAt
) {
    public static CommentDto from(Comment comment) {
        Long parentCommentId = null;
        if (comment.getParentComment() != null) {
            parentCommentId = comment.getParentComment().getId();
        }
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getOwnerId(),
                comment.getUser().getName(),
                comment.getPost().getId(),
                parentCommentId,
                comment.getCreatedAt()
        );
    }
}
