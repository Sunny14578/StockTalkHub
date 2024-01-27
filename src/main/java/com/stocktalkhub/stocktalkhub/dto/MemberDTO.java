package com.stocktalkhub.stocktalkhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private String password;
    private String email;
    private String auth;
    private String profile_image;
    private String introduce;
}
