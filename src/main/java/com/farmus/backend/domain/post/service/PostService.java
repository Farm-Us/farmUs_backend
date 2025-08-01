package com.farmus.backend.domain.post.service;

import com.farmus.backend.domain.global.s3.S3Service;
import com.farmus.backend.domain.post.dto.PostCreateRequest;
import com.farmus.backend.domain.post.dto.PostDto;
import com.farmus.backend.domain.post.dto.PostUpdateRequest;
import com.farmus.backend.domain.post.entity.Post;
import com.farmus.backend.domain.post.entity.PostMedia;
import com.farmus.backend.domain.post.repository.PostMediaRepository;
import com.farmus.backend.domain.post.repository.PostRepository;
import com.farmus.backend.domain.user.entity.ProducerProfile;
import com.farmus.backend.domain.user.repository.ProducerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final ProducerProfileRepository producerProfileRepository;
    private final PostMediaRepository postMediaRepository;
    private final S3Service s3Service;

    public PostDto createPost(Long producerId, PostCreateRequest postCreateRequest, List<MultipartFile> imageFiles) {
        ProducerProfile producer = producerProfileRepository.findOrThrow(producerId);

        List<String> mediaUrls = uploadImages(imageFiles);
        validateMediaUrls(mediaUrls);

        Post post = Post.of(producer, postCreateRequest.title(), postCreateRequest.content());
        postRepository.save(post);

        saveMediaList(post, mediaUrls);

        return PostDto.of(post);
    }

    @Transactional(readOnly = true)
    public Page<PostDto> getPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(post -> {
            List<String> mediaUrls = postMediaRepository.findAllByPost(post).stream()
                    .map(PostMedia::getMediaUrl)
                    .toList();
            return PostDto.of(post);
        });
    }

    @Transactional(readOnly = true)
    public PostDto getPostDetail(Long postId) {
        Post post = postRepository.findOrThrow(postId);
        List<String> mediaUrls = postMediaRepository.findAllByPost(post).stream()
                .map(PostMedia::getMediaUrl)
                .toList();
        return PostDto.of(post);
    }

    public PostDto updatePost(Long producerId, Long postId, PostUpdateRequest postUpdateRequest, List<MultipartFile> imageFiles) {
        ProducerProfile producer = producerProfileRepository.findOrThrow(producerId);
        Post post = postRepository.findOrThrow(postId);

        //NPE 방지
        List<String> keepMediaUrls = postUpdateRequest.keepMediaUrls() != null
                ? postUpdateRequest.keepMediaUrls()
                : List.of();

        deleteRemovedImages(post, keepMediaUrls);

        List<String> newMdUrls = uploadImages(imageFiles);

        savePostMedia(post, newMdUrls);
        post.update(postUpdateRequest.title(), postUpdateRequest.content());

        List<String> mediaUrls = postMediaRepository.findAllByPost(post)
                .stream()
                .map(PostMedia::getMediaUrl).toList();

        return PostDto.of(post);
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findOrThrow(postId);

        List<PostMedia> mediaList = postMediaRepository.findAllByPost(post);

        for(PostMedia media : mediaList) {
            s3Service.deleteFile(media.getMediaUrl());
        }
        postRepository.delete(post);
    }

    private List<String> uploadImages(List<MultipartFile> imageFiles) {
        return imageFiles.stream()
                .map(file -> {
                    try{
                        return s3Service.uploadFile(file);
                    } catch (IOException e) {
                        throw new RuntimeException("S3 업로드 실패", e);
                    }
                })
                .toList();
    }

    private List<PostMedia> saveMediaList(Post post, List<String> mediaUrls) {
        List<PostMedia> mediaList = mediaUrls.stream()
                .map(url -> PostMedia.of(post, url))
                .toList();
        postMediaRepository.saveAll(mediaList);
        return mediaList;
    }

    private void validateMediaUrls(List<String> mediaUrls) {
        if (mediaUrls == null || mediaUrls.isEmpty()) {
            throw new IllegalArgumentException("MediaUrls cannot be empty");
        }
    }

    private void deleteRemovedImages(Post post, List<String> keepMediaUrls) {
        List<PostMedia> allPosts = postMediaRepository.findAllByPost(post);
        List<PostMedia> toDelete = allPosts.stream()
                .filter(media -> !keepMediaUrls.contains(media.getMediaUrl()))
                .toList();

        for (PostMedia media : toDelete) {
            s3Service.deleteFile(media.getMediaUrl());
        }
        postMediaRepository.deleteAll(toDelete);
    }

    private List<String> uploadNewImages(List<MultipartFile> imageFiles) {
        if (imageFiles == null || imageFiles.isEmpty()) return List.of();
        return imageFiles.stream()
                .map(file -> {
                    try {
                        return s3Service.uploadFile(file);
                    } catch (IOException e) {
                        throw new RuntimeException("S3 업로드 실패", e);
                    }
                })
                .toList();
    }

    private void savePostMedia(Post post, List<String> mediaUrls) {
        List<PostMedia> newMedia = mediaUrls.stream()
                .map(url -> PostMedia.of(post, url))
                .toList();
        postMediaRepository.saveAll(newMedia);
    }

}
