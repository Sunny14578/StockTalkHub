package com.stocktalkhub.stocktalkhub.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter @Setter
public class Like {

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
