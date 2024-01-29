package com.stocktalkhub.stocktalkhub.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
public class Like {
    @Id
    @GeneratedValue
    @Column(name = "like_id")
    private Long id;

    @ManyToOne
    @JoinColumn
    private Member member_id;

    @ManyToOne
    @JoinColumn
    private Post post_id;

    private LocalDateTime created_at;
    private LocalDateTime deleted_at;
    private LocalDateTime updated_at;
}
