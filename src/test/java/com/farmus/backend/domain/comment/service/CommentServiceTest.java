package com.farmus.backend.domain.comment.service;

import com.farmus.backend.domain.comment.entity.Comment;
import com.farmus.backend.domain.comment.repository.CommentRepository;
import com.farmus.backend.domain.post.entity.Post;
import com.farmus.backend.domain.post.repository.PostRepository;
import com.farmus.backend.domain.user.entity.ProducerProfile;
import com.farmus.backend.domain.user.entity.User;
import com.farmus.backend.domain.user.repository.ProducerProfileRepository;
import com.farmus.backend.domain.user.repository.UserRepository;
import com.farmus.backend.support.annotation.ServiceTest;
import com.farmus.backend.support.fixture.CommentFixture;
import com.farmus.backend.support.fixture.PostFixture;
import com.farmus.backend.support.fixture.ProducerFixture;
import com.farmus.backend.support.fixture.UserFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

@ServiceTest
public class CommentServiceTest {

    @Autowired
    CommentService commentService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProducerProfileRepository producerProfileRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;

    @Test
    void 댓글을_작성할_수_있다(){
        User user = UserFixture.김회원();
        userRepository.save(user);
        ProducerProfile producer = ProducerFixture.생산자(user);
        producerProfileRepository.save(producer);
        Post post = PostFixture.게시글(producer);
        postRepository.save(post);
        Comment comment1 = CommentFixture.댓글1(user, post);
        commentRepository.save(comment1);
        assertThat(commentRepository.findAll()).hasSize(1);
    }

    @Test
    void 대댓글을_작성할_수_있다() {
        User user = UserFixture.김회원();
        userRepository.save(user);
        ProducerProfile producer = ProducerFixture.생산자(user);
        producerProfileRepository.save(producer);
        Post post = PostFixture.게시글(producer);
        postRepository.save(post);
        Comment comment1 = CommentFixture.댓글1(user, post);
        commentRepository.save(comment1);

        Comment comment2 = CommentFixture.댓글2(user, post, comment1);
        commentRepository.save(comment2);

        assertThat(commentRepository.findAll()).hasSize(2);
        assertThat(comment2.getParentComment().getId()).isEqualTo(comment1.getId());
    }

    @Test
    void 댓글을_삭제할_수_있다() {
        User user = UserFixture.김회원();
        userRepository.save(user);
        ProducerProfile producer = ProducerFixture.생산자(user);
        producerProfileRepository.save(producer);
        Post post = PostFixture.게시글(producer);
        postRepository.save(post);
        Comment comment1 = CommentFixture.댓글1(user, post);
        commentRepository.save(comment1);
        assertThat(commentRepository.findAll()).hasSize(1);

        commentService.deleteComment(comment1.getId());
        assertThat(commentRepository.findAll()).hasSize(0);
    }
}
