package com.stocktalkhub.stocktalkhub.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class MessageWithData {
    private String message;
    private PostsDTO post;
    private CommentDTO comment;

    @Builder
    private MessageWithData(String message, PostsDTO post, CommentDTO comment) {
        this.message = message;
        this.post = post;
        this.comment = comment;
    }
}
