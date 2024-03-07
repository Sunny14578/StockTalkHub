package com.stocktalkhub.stocktalkhub.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class JwtTokenizerTest {
    @Autowired
//    JwtTokenizer jwtTokenizer;

    @Value("${jwt.secretKey}")
    String accessSecret;
    public final Long ACCESS_TOKEN_EXPIRE_COUNT = 30 * 60 * 1000L;

    @Test
    public void createToken() throws Exception{
        String email = "choitaeyang0101@gmail.com";
        List<String> roles = List.of("ROLE_USER");
        Long id= 1L;
//        claims : jwt 토큰의 payload에 들어갈 내용
        Claims claims = Jwts.claims().setSubject(email);

//     이미 존재하는 subject은 set으로 처리

        claims.put("roles", roles);
        claims.put("userId", id);

        byte[] accessSecret = this.accessSecret.getBytes(StandardCharsets.UTF_8);

        // JWT 생성
        String JwtToken = Jwts.builder() // JwtBuilder 반환
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + this.ACCESS_TOKEN_EXPIRE_COUNT)) // 30분뒤만료
                .signWith(Keys.hmacShaKeyFor(accessSecret)) // 결과에 서명까지 포함시킨 JWTBuilder리턴
                .compact();
        System.out.println(JwtToken);
    }
}
