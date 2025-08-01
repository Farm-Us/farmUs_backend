package com.farmus.backend.support.fixture;

import com.farmus.backend.domain.post.dto.PostCreateRequest;
import com.farmus.backend.domain.post.entity.Post;
import com.farmus.backend.domain.user.entity.ProducerProfile;
import lombok.Getter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public enum PostFixture {

    이미지_포함_게시글(
            "테스트 제목",
            "테스트 내용",
            List.of(
                    new MockMultipartFile("images", "img1.png", "image/png", "a".getBytes()),
                    new MockMultipartFile("images", "img2.png", "image/png", "b".getBytes())
            )
    );

    private final String title;
    private final String content;
    private final List<MultipartFile> mediaUrls;

    PostFixture(String title, String content, List<MultipartFile> mediaUrls) {
        this.title = title;
        this.content = content;
        this.mediaUrls = mediaUrls;
    }

    public PostCreateRequest toPostCreateRequest() {
        return new PostCreateRequest(
                title, content
        );
    }

    public static Post 게시글(ProducerProfile producer){
        return Post.of(producer, 이미지_포함_게시글.title, 이미지_포함_게시글.content);
    }

}
