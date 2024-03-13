package com.stocktalkhub.stocktalkhub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final RedisService redisService;

    @Async
    public boolean sendMailReject(String email) throws Exception{
        boolean msg = false;
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email); // 상대방 이메일 주소
        simpleMailMessage.setSubject("StockTalkHub 회원가입 인증 메일");
        simpleMailMessage.setFrom("choitaeyang0101@gmail.com");


        String verificationCode = generateRandomCode();

        simpleMailMessage.setText("인증번호 : " + verificationCode);


        javaMailSender.send(simpleMailMessage);
        redisService.setValues(email, verificationCode);

        return true;
    }

    private static final int CODE_LENGTH = 6;
    private static final String DIGITS = "0123456789";

    public static String generateRandomCode() {
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(DIGITS.length());
            codeBuilder.append(DIGITS.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
}
