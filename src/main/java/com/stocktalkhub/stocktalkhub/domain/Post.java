package com.stocktalkhub.stocktalkhub.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
public class Post {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumn
    private Member member_id;

    private String content;
    private String media_file;
    private LocalDateTime created_at;
    private LocalDateTime deleted_at;
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "post_id")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post_id")
    private List<Like> likes = new ArrayList<>();
}
