package com.stocktalkhub.stocktalkhub.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Members")
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String introduce;
    private String password;
    private String email;
    private boolean is_verified;
    private LocalDateTime created_at;
    private LocalDateTime deleted_at;
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "follower")
    private List<Follow> following;

    @OneToMany(mappedBy = "following")
    private List<Follow> followers;

    @OneToMany(mappedBy = "member_id")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member_id")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member_id")
    private List<Like> likes = new ArrayList<>();
}
