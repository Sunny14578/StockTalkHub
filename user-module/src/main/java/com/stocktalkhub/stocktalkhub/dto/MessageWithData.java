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
    private Long postId;
    private Long commentId;

    @Builder
    private MessageWithData(String message, Long postId, Long commentId) {
        this.message = message;
        this.postId = postId;
        this.commentId = commentId;
    }
}
