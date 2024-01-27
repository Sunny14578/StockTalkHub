package com.stocktalkhub.stocktalkhub.service;

import com.stocktalkhub.stocktalkhub.domain.Member;
import com.stocktalkhub.stocktalkhub.dto.MemberDTO;
import com.stocktalkhub.stocktalkhub.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    private static final String AUTH_CODE_PREFIX = "AuthCode ";
    private final RedisService redisService;

    public void sendCodeToEmail(MemberDTO member) throws Exception {
        String userEmail = member.getEmail();
        this.validateDuplicateMember(userEmail);
        emailService.sendMailReject(userEmail);
        String Authcode = redisService.getValues(userEmail);
    }

    // 회원 가입
    @Transactional
    public Long join(MemberDTO member){
        this.validateDuplicateMember(member.getEmail());
        verifiedCode(member.getEmail(), member.getAuth());

        String encodedPassword = passwordEncoder.encode(member.getPassword());

        Member entityMember = Member.builder()
                .password(encodedPassword)
                .email(member.getEmail())
                .profile_image(member.getProfile_image())
                .introduce(member.getIntroduce())
                .is_verified(true)
                .build();

        memberRepository.save(entityMember);

        return memberRepository.findByEmail(entityMember.getEmail()).getId();
    }



    private void validateDuplicateMember(String email) {
        Member findMember = memberRepository.findByEmail(email);
        /*  */
        if (findMember != null) {
            throw new EmailAlreadyExistsException(email);
        }
    }


    public boolean verifiedCode(String email, String authCode) {

        String redisAuthCode = redisService.getValues(email);
        System.out.print(email + " " + authCode + " " + redisAuthCode + "확인====");

        if (redisAuthCode == null) {
            throw new ExpiredAuthCodeException("인증 코드가 만료되었습니다.");
        }

        if (!redisService.checkExistsValue(redisAuthCode) || !redisAuthCode.equals(authCode)) {
            throw new InvalidAuthCodeException("인증 코드가 일치하지 않습니다.");
        }

        return true;
    }

    public class ExpiredAuthCodeException extends RuntimeException {
        public ExpiredAuthCodeException(String message) {
            super(message);
        }
    }

    public class InvalidAuthCodeException extends RuntimeException {
        public InvalidAuthCodeException(String message) {
            super(message);
        }
    }

    public class EmailAlreadyExistsException extends RuntimeException {
        public EmailAlreadyExistsException(String email) {
            super("Email already exists: " + email);
        }
    }




}
