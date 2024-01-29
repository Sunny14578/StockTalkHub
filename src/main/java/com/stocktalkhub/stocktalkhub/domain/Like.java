package com.stocktalkhub.stocktalkhub.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "likes")
@Getter
public class Like {
    @Id
    @GeneratedValue
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Post post_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Comment comment_id;

    private LocalDateTime created_at;
    private LocalDateTime deleted_at;
    private LocalDateTime updated_at;
}
