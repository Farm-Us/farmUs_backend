package com.farmus.backend.support.fixture;

import com.farmus.backend.domain.comment.entity.Comment;
import com.farmus.backend.domain.post.entity.Post;
import com.farmus.backend.domain.user.entity.User;
import lombok.Getter;

@Getter
public enum CommentFixture {

    댓글1("테스트 댓글입니다."),
    댓글2("테스트 대댓글입니다.");


    private String content;

    CommentFixture(String content) {
        this.content = content;
    }

    public static Comment 댓글1(User user, Post post) {
        return Comment.of(user, post, 댓글1.content, null);
    }

    public static Comment 댓글2(User user, Post post, Comment parentComment) {
        return Comment.of(user, post, 댓글2.content, parentComment);
    }

}
