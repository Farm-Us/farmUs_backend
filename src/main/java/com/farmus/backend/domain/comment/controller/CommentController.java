package com.farmus.backend.domain.comment.controller;

import com.farmus.backend.domain.comment.dto.CommentCreateRequest;
import com.farmus.backend.domain.comment.dto.CommentDto;
import com.farmus.backend.domain.comment.dto.CommentUpdateRequest;
import com.farmus.backend.domain.comment.exception.CommentSuccessCode;
import com.farmus.backend.domain.comment.service.CommentService;
import com.farmus.backend.domain.global.success.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments/{postId}")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{userId}")
    public ResponseEntity<SuccessResponse<CommentDto>> createComment(
            @RequestBody CommentCreateRequest createRequest,
            @PathVariable Long postId,
            @PathVariable Long userId) {
        CommentDto comment = commentService.createComment(userId, postId, createRequest);
        return ResponseEntity.status(CommentSuccessCode.COMMENT_CREATED.getStatus())
                .body(SuccessResponse.of(CommentSuccessCode.COMMENT_CREATED, comment));
    }

    @GetMapping
    public ResponseEntity<Page<CommentDto>> getComments(
            @PathVariable Long postId,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        Page<CommentDto> comments = commentService.getComments(postId, pageable);
        return ResponseEntity.ok(comments);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequest updateRequest
    ) {
        CommentDto comment = commentService.updateComment(commentId, updateRequest);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ){
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
