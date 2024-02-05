package com.stocktalkhub.stocktalkhub.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "newsFeeds")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsFeed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "postLike_id")
    private Long postLikeId;

    @Column(name = "commentLike_id")
    private Long commentLikeId;

    @Column(name = "follow_id")
    private Long followId;

    @Enumerated(EnumType.STRING)
    private NewsFeedType type;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "receiver_id")
    private Long receiverId;

    private LocalDateTime timestamp;

    @Builder
    private NewsFeed(Long postId, Long commentId,
                    Long postLikeId, Long commentLikeId,
                    Long followId, NewsFeedType type,
                    Long senderId, Long receiverId,
                    LocalDateTime timestamp) {
        this.postId = postId;
        this.commentId = commentId;
        this.postLikeId = postLikeId;
        this.commentLikeId = commentLikeId;
        this.followId = followId;
        this.type = type;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.timestamp = timestamp;
    }
}

