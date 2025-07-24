package com.farmus.backend.domain.post.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post_media")
public class PostMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(length = 1024)
    private String mediaUrl;

    private PostMedia(Post post, String mediaUrl) {
        this.post = post;
        this.mediaUrl = mediaUrl;
    }

    public static PostMedia of(Post post, String mediaUrl) { return new PostMedia(post, mediaUrl); }
}
