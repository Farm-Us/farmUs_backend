package com.farmus.backend.domain.post.controller;

import com.farmus.backend.domain.global.success.SuccessResponse;
import com.farmus.backend.domain.post.dto.PostCreateRequest;
import com.farmus.backend.domain.post.dto.PostDto;
import com.farmus.backend.domain.post.dto.PostUpdateRequest;
import com.farmus.backend.domain.post.exception.PostSuccessCode;
import com.farmus.backend.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    @PostMapping(value = "/{producerId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse<PostDto>> createPost(
            @PathVariable Long producerId,
            @RequestPart("postCreateRequest") PostCreateRequest postCreateRequest,
            @RequestPart("images") List<MultipartFile> images) {
        PostDto post = postService.createPost(producerId, postCreateRequest, images);
        return ResponseEntity.status(PostSuccessCode.POST_CREATED.getStatus())
                .body(SuccessResponse.of(PostSuccessCode.POST_CREATED, post));
    }

    @GetMapping
    public ResponseEntity<Page<PostDto>> getPosts(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(postService.getPosts(pageable));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostDetail(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostDetail(postId));
    }

    @PatchMapping(value = "/{producerId}/{postId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDto> updatePost(
            @PathVariable Long postId,
            @PathVariable Long producerId,
            @RequestPart("postUpdateRequest") PostUpdateRequest postUpdateRequest,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        PostDto post = postService.updatePost(postId, producerId, postUpdateRequest, images);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
