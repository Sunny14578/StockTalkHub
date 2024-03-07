package com.stocktalkhub.stocktalkhub.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class MessageWithData {
    private String message;
    private Long postId;
    private Long commentId;
    private Long followId;
    private Long postLikeId;
    private Long commentLikeId;

    @Builder
    public MessageWithData(String message, Long postId, Long commentId,
                           Long followId, Long postLikeId, Long commentLikeId,
                           LocalDateTime timestamp) {
        this.message = message;
        this.postId = postId;
        this.commentId = commentId;
        this.followId = followId;
        this.postLikeId = postLikeId;
        this.commentLikeId = commentLikeId;
        this.timestamp = timestamp;
    }

    private LocalDateTime timestamp;

}
