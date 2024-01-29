package com.stocktalkhub.stocktalkhub.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDTO {
    private Long memberId;
    private String email;
    private String accessToken;
    private String refreshToken;

    @Builder
    public TokenDTO(Long memberId, String email, String accessToken, String refreshToken) {
        this.memberId = memberId;
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}