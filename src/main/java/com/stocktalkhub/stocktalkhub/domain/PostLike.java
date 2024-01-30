package com.stocktalkhub.stocktalkhub.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "postLikes")
@Getter
public class PostLike {
    @Id
    @GeneratedValue
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post postId;

    private LocalDateTime created_at;
    private LocalDateTime deleted_at;
    private LocalDateTime updated_at;

    @Builder
    public PostLike(Member memberId, Post postId, LocalDateTime created_at) {
        this.memberId = memberId;
        this.postId = postId;
        this.created_at = created_at;
    }
}
