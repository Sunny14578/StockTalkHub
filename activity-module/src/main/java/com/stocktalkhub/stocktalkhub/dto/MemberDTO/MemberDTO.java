package com.stocktalkhub.stocktalkhub.dto.MemberDTO;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long id;
    private String password;
    private String email;
    private String auth;
    private String profile_image;
    private String introduce;
    private String name;

    @Builder
    public MemberDTO(Long id, String password, String introduce, String profile_image, String name) {
        this.id = id;
        this.password = password;
        this.introduce = introduce;
        this.profile_image = profile_image;
        this.name = name;
    }
}
