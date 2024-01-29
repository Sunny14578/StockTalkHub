package com.stocktalkhub.stocktalkhub.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    private String content;
    private String media_file;
    private LocalDateTime created_at;
    private LocalDateTime deleted_at;
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "postId")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "postId")
    private List<Like> likes = new ArrayList<>();

    @Builder
    public Post(Member member_id, String content, LocalDateTime created_at) {
        this.memberId = member_id;
        this.content = content;
        this.created_at = created_at;
    }

}
