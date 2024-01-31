package com.stocktalkhub.stocktalkhub.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

//    private LocalDateTime deleted_at;
//    private LocalDateTime updated_at;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "post_id")
    private Long postId;

    @Builder
    public Comment(Long memberId, Long post_id, String content, LocalDateTime createdAt) {
        this.memberId = memberId;
        this.postId = post_id;
        this.content = content;
        this.createdAt = createdAt;
    }
}
