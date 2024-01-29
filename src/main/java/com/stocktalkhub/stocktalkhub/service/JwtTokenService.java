package com.stocktalkhub.stocktalkhub.service;

import com.stocktalkhub.stocktalkhub.domain.Member;
import com.stocktalkhub.stocktalkhub.security.jwt.util.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenService {
    private final RedisService redisService;

    public void deleteRefreshToken(User user) {
        String key = "refresh_token_"+user.getId();
        String refreshToken = redisService.getValues(key);

        // 레디스에 해당 키가 존재하는지 확인
        if (refreshToken != null) {
            // 존재하면 해당 키를 삭제
            redisService.deleteValues(key);
            System.out.println("Refresh token deleted successfully.");
        } else {
            System.out.println("Refresh token does not exist.");
        }
    }

    public void saveTokenRedis(String accessToken, String refreshToken, Member member) {
        redisService.setValues("access_token_" + member.getId(), accessToken);
        redisService.setValues("refresh_token_" + member.getId(), refreshToken);

//        System.out.printf(this.getValues("access_token_" + member.getId()));
//        System.out.printf(this.getValues("refresh_token_" + member.getId()));
    }
}
