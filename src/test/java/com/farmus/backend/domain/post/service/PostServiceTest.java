package com.farmus.backend.domain.post.service;

import com.farmus.backend.domain.global.s3.S3Service;
import com.farmus.backend.domain.post.dto.PostCreateRequest;
import com.farmus.backend.domain.post.dto.PostDto;
import com.farmus.backend.domain.post.entity.Post;
import com.farmus.backend.domain.post.entity.PostMedia;
import com.farmus.backend.domain.post.repository.PostMediaRepository;
import com.farmus.backend.domain.post.repository.PostRepository;
import com.farmus.backend.domain.user.entity.ProducerProfile;
import com.farmus.backend.domain.user.entity.User;
import com.farmus.backend.domain.user.repository.ProducerProfileRepository;
import com.farmus.backend.domain.user.repository.UserRepository;
import com.farmus.backend.support.annotation.ServiceTest;
import com.farmus.backend.support.fixture.PostFixture;
import com.farmus.backend.support.fixture.ProducerFixture;
import com.farmus.backend.support.fixture.UserFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ServiceTest
public class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProducerProfileRepository producerProfileRepository;
    @Autowired
    PostMediaRepository postMediaRepository;

    @MockitoBean
    S3Service s3Service;

    @Transactional
    @Test
    void 이미지를_포함한_게시글을_작성할_수_있다(){
        User user = UserFixture.김회원();
        userRepository.save(user);
        ProducerProfile producer = ProducerFixture.생산자(user);
        producerProfileRepository.save(producer);

//        Post post = PostFixture.게시글(producer);
//        postRepository.save(post);
//
//
//        Post savedPost = postRepository.findAll().get(0);
//        assertThat(savedPost.getMediaList()).hasSize(2);
//        List<String> urls = savedPost.getMediaList().stream()
//                .map(PostMedia::getMediaUrl)
//                .toList();
//        assertThat(urls).contains("img1.png", "img2.png");

        PostFixture postFixture = PostFixture.이미지_포함_게시글;
        PostCreateRequest postCreateRequest = postFixture.toPostCreateRequest();
        List<MultipartFile> files = postFixture.getMediaUrls();

        try {
            Mockito.when(s3Service.uploadFile(any(MultipartFile.class)))
                    .thenReturn("http://mock-s3-url/img1.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PostDto postDto = postService.createPost(producer.getId(), postCreateRequest, files);

        assertEquals(postFixture.getTitle(), postDto.title());
        assertEquals(postFixture.getContent(), postDto.content());
        assertEquals(producer.getId(), postDto.producerId());


    }
}
