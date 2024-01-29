package com.stocktalkhub.stocktalkhub.service;

import com.stocktalkhub.stocktalkhub.domain.Member;
import com.stocktalkhub.stocktalkhub.dto.MemberDTO;
import com.stocktalkhub.stocktalkhub.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;

    private static final String AUTH_CODE_PREFIX = "AuthCode ";
    private final RedisService redisService;

//    유저정보 업데이트
    @Transactional(readOnly = false)
    public void MemberInfoUpdate(Long id, MemberDTO member) {

        Member Entitymember = memberRepository.findOne(id).orElseThrow(() ->
                new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));;
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        System.out.println("여기까지 오니?");

        Entitymember.update(
                encodedPassword,
                member.getIntroduce(),
                member.getProfile_image(),
                member.getName()
        );
        System.out.println(encodedPassword + " " + member.getIntroduce() + " " + member.getProfile_image());
    }


    // 이메일 인증코드 전송
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

        return entityMember.getId();
    }


    // 이메일 중복체크
    private void validateDuplicateMember(String email) {
        Member findMember = memberRepository.findByEmail(email);
        /*  */
        if (findMember != null) {
            throw new EmailAlreadyExistsException(email);
        }
    }

    // 인증코드 일치 체크
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

    public Member findById(Long id){
        Member member = memberRepository.findOne(id).orElseThrow(() ->
                new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        return member;
    }

    public Member findByEmail(String email) {
        Member findMember = memberRepository.findByEmail(email);

        /*  */
        if (findMember == null) {
            throw new EmailNotExistsException();
        }
        return findMember;
    }


    public void login(String memberPassword, String loginPassword) {

        if(!passwordEncoder.matches(memberPassword, loginPassword)){
            throw new PasswordMismatchException();
        }
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

    public class EmailNotExistsException extends RuntimeException {
        public EmailNotExistsException() {
            super("Email does not exists: 회원가입을 진행해주세요.");
        }
    }

    public class PasswordMismatchException extends RuntimeException {
        public PasswordMismatchException() {
            super("비밀번호가 일치 하지 않습니다.");
        }
    }

    public class AccessTokenMismatch extends RuntimeException {
        public AccessTokenMismatch() {
            super("유저가 일치하지 않습니다.");
        }
    }




}
