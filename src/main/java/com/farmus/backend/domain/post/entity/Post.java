package com.farmus.backend.domain.post.entity;

import com.farmus.backend.domain.global.BaseEntity;
import com.farmus.backend.domain.user.entity.ProducerProfile;
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
    @JoinColumn(name = "producer_profile_id")
    private ProducerProfile producerProfile;

    private String title;

    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostMedia> mediaList = new ArrayList<>();

    private Post(ProducerProfile producerProfile, String title, String content) {
        this.producerProfile = producerProfile;
        this.title = title;
        this.content = content;
    }
    public Post(String title, String content) {
        this(null, title, content);
    }

    public static Post of(ProducerProfile producerProfile, String title, String content) {
        return new Post(producerProfile, title, content);
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Long getOwnerId() {
        return producerProfile.getId();
    }
}
