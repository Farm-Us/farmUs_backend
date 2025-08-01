package com.farmus.backend.domain.post.dto;

import java.util.List;

public record PostUpdateRequest(
        String title,
        String content,
        List<String> keepMediaUrls
) {
}
