package com.farmus.backend.domain.post.entity;

import com.farmus.backend.domain.global.BaseEntity;
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
@Table(name = "posts")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostMedia> mediaList = new ArrayList<>();

    private Post(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public static Post of(User user, String title, String content) {
        return new Post(user, title, content);
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Long getOwnerId() {
        return user.getId();
    }
}
