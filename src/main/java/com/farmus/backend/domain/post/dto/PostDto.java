package com.farmus.backend.domain.post.dto;

import com.farmus.backend.domain.post.entity.Post;
import com.farmus.backend.domain.post.entity.PostMedia;

import java.time.LocalDateTime;
import java.util.List;

public record PostDto(
        Long postId,
        String title,
        String content,
        Long producerId,
        List<String> mediaUrls,
        LocalDateTime createdAt
) {
    public static PostDto of(Post post) {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getOwnerId(),
                post.getMediaList().stream()
                        .map(PostMedia::getMediaUrl)
                        .toList(),
                post.getCreatedAt()
        );
    }
}
