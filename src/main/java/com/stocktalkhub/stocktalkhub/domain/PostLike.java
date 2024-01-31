package com.stocktalkhub.stocktalkhub.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "postLikes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
//    private LocalDateTime deleted_at;
//    private LocalDateTime updated_at;

    @Builder
    public PostLike(Long memberId, Long postId, LocalDateTime createdAt) {
        this.memberId = memberId;
        this.postId = postId;
        this.createdAt = createdAt;
    }
}
