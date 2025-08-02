package com.farmus.backend.domain.comment.service;

import com.farmus.backend.domain.comment.dto.CommentCreateRequest;
import com.farmus.backend.domain.comment.dto.CommentDto;
import com.farmus.backend.domain.comment.dto.CommentUpdateRequest;
import com.farmus.backend.domain.comment.entity.Comment;
import com.farmus.backend.domain.comment.repository.CommentRepository;
import com.farmus.backend.domain.post.entity.Post;
import com.farmus.backend.domain.post.repository.PostRepository;
import com.farmus.backend.domain.user.entity.User;
import com.farmus.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentDto createComment(Long userId, Long postId, CommentCreateRequest createRequest) {
        User user = userRepository.findOrThrow(userId);
        Post post = postRepository.findOrThrow(postId);

        Comment parentComment = findParentCommentOrNull(createRequest.getParentCommentId());

        Comment comment = Comment.of(user, post, createRequest.getContent(), parentComment);
        commentRepository.save(comment);

        return CommentDto.from(comment);
    }

    public Page<CommentDto> getComments(Long postId, Pageable pageable) {
        return commentRepository.findAllByPostIdOrderByIdDesc(postId, pageable)
                .map(CommentDto::from);
    }

    public CommentDto updateComment(Long commentId, CommentUpdateRequest updateRequest) {
        Comment comment = commentRepository.findOrThrow(commentId);
        comment.update(updateRequest.getContent());
        commentRepository.save(comment);
        return CommentDto.from(comment);
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findOrThrow(commentId);
        commentRepository.delete(comment);
    }

    private Comment findParentCommentOrNull(Long parentCommentId) {
        if(parentCommentId == null) return null;
        return commentRepository.findOrThrow(parentCommentId);
    }



}
