package com.stocktalkhub.stocktalkhub.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postLike_id")
    private PostLike postLike;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentLike_id")
    private CommentLike commentLike;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_id")
    private Follow follow;

    @Enumerated(EnumType.STRING)
    private NewsFeedType type;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    private LocalDateTime timestamp;

    @Builder
    public NewsFeed(Post post, Comment comment,
                    PostLike postLike, CommentLike commentLike,
                    Follow follow, NewsFeedType type, Member sender,
                    Member receiver, LocalDateTime timestamp) {
        this.post = post;
        this.comment = comment;
        this.postLike = postLike;
        this.commentLike = commentLike;
        this.follow = follow;
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
    }
}
