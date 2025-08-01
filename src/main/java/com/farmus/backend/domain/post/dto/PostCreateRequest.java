package com.farmus.backend.domain.post.dto;

import java.util.List;

public record PostCreateRequest(
        String title,
        String content
) {
}
