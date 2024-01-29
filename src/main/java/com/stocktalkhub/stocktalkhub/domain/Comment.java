package com.stocktalkhub.stocktalkhub.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor
public class Comment {
    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;


    private String content;
    private LocalDateTime created_at;
    private LocalDateTime deleted_at;
    private LocalDateTime updated_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post postId;

    @OneToMany(mappedBy = "commentId")
    private List<Like> Likes = new ArrayList<>();



    @Builder
    public Comment(Member member_id, Post post_id, String content, LocalDateTime created_at) {
        this.memberId = member_id;
        this.postId = post_id;
        this.content = content;
        this.created_at = created_at;
    }
}
