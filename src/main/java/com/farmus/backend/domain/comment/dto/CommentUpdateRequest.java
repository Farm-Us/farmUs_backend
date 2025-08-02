package com.farmus.backend.domain.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentUpdateRequest {

    @NotNull
    private String content;

    public CommentUpdateRequest(String content) {
        this.content = content;
    }
}
