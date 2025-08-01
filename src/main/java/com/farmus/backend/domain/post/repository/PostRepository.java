package com.farmus.backend.domain.post.repository;

import com.farmus.backend.domain.post.entity.Post;
import com.farmus.backend.domain.post.exception.PostErrorCode;
import com.farmus.backend.domain.post.exception.PostException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByProducerProfileIdOrderByCreatedAtDesc(Long producerProfileId, Pageable pageable);
    Page<Post> findAll(Pageable pageable);

    default Post findOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));
    }
}
