package com.stocktalkhub.stocktalkhub.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ActivityMemberDTO {
    private String password;
    private String email;
    private String auth;
    private String profile_image;
    private String introduce;
    private String name;

    @Builder
    public ActivityMemberDTO(String password, String introduce, String profile_image, String name) {
        this.password = password;
        this.introduce = introduce;
        this.profile_image = profile_image;
        this.name = name;
    }
}
