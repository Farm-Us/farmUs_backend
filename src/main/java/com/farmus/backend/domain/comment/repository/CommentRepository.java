package com.farmus.backend.domain.comment.repository;

import com.farmus.backend.domain.comment.entity.Comment;
import com.farmus.backend.domain.comment.exception.CommentErrorCode;
import com.farmus.backend.domain.comment.exception.CommentException;
import com.farmus.backend.domain.post.entity.Post;
import com.farmus.backend.domain.post.exception.PostErrorCode;
import com.farmus.backend.domain.post.exception.PostException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByPostIdOrderByIdDesc(Long postId, Pageable pageable);

    default Comment findOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));
    }
}
