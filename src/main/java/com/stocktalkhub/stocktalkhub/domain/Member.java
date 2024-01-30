package com.stocktalkhub.stocktalkhub.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Members")
@Getter
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String introduce;
    private String password;
    private String profile_image;
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role = Role.ROLE_USER;

    @NotEmpty
    @Column(name = "email", unique = true)
    private String email;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean is_verified;


    private LocalDateTime created_at;
    private LocalDateTime deleted_at;
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "follower")
    private List<Follow> following;

    @OneToMany(mappedBy = "following")
    private List<Follow> followers;

    @OneToMany(mappedBy = "memberId")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "memberId")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "memberId")
    private List<PostLike> likes = new ArrayList<>();

    @Builder
    public Member(String password, String email, boolean is_verified, String introduce, String profile_image, String name) {
        this.password = password;
        this.email = email;
        this.introduce = introduce;
        this.profile_image = profile_image;
        this.is_verified = is_verified;
        this.name = name;
    }

    public void update(String password, String introduce, String profile_image, String name){
        this.password = password;
        this.introduce = introduce;
        this.profile_image = profile_image;
        this.name = name;
    }


}
