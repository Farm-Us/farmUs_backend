package com.farmus.backend.domain.comment.entity;

import com.farmus.backend.domain.global.BaseEntity;
import com.farmus.backend.domain.post.entity.Post;
import com.farmus.backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", orphanRemoval = true)
    private List<Comment> childrenComment = new ArrayList<>();

    private Comment(User user, Post post, String content, Comment parentComment) {
        this.user = user;
        this.post = post;
        this.content = content;
        this.parentComment = parentComment;
    }

    public static Comment of(User user, Post post, String content, Comment parentComment) {
        return new Comment(user, post, content, parentComment);
    }

    public void update(String content) {
        this.content = content;
    }

    public Long getOwnerId() {
        return user.getId();
    }

}
