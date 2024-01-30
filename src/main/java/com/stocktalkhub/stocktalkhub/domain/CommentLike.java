package com.stocktalkhub.stocktalkhub.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CommentLikes")
@Getter
public class CommentLike {
    @Id
    @GeneratedValue
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment commentId;

    private LocalDateTime created_at;
    private LocalDateTime deleted_at;
    private LocalDateTime updated_at;

    @Builder
    public CommentLike(Member memberId, Comment commentId, LocalDateTime created_at) {
        this.memberId = memberId;
        this.commentId = commentId;
        this.created_at = created_at;
    }
}
