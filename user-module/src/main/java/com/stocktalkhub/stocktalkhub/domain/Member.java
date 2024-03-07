package com.stocktalkhub.stocktalkhub.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "introduce")
    private String introduce;

    @Column(name = "password")
    private String password;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role = Role.ROLE_USER;

    @NotEmpty
    @Column(name = "email", unique = true)
    private String email;

//    private LocalDateTime created_at;
//    private LocalDateTime deleted_at;
//    private LocalDateTime updated_at;

    @Builder
    public Member(String password, String email, boolean is_verified, String introduce, String profileImage, String name) {
        this.password = password;
        this.email = email;
        this.introduce = introduce;
        this.profileImage = profileImage;
        this.name = name;
    }

    public void update(String password, String introduce, String profileImage, String name){
        this.password = password;
        this.introduce = introduce;
        this.profileImage = profileImage;
        this.name = name;
    }


}
