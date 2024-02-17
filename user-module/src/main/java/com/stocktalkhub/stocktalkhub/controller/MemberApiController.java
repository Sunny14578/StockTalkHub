package com.stocktalkhub.stocktalkhub.controller;

import com.stocktalkhub.stocktalkhub.domain.Member;
import com.stocktalkhub.stocktalkhub.dto.MemberDTO;
import com.stocktalkhub.stocktalkhub.dto.TokenDTO;
import com.stocktalkhub.stocktalkhub.security.jwt.util.JwtTokenizer;
import com.stocktalkhub.stocktalkhub.security.jwt.util.User;
import com.stocktalkhub.stocktalkhub.service.JwtTokenService;
import com.stocktalkhub.stocktalkhub.service.MemberService;
import com.stocktalkhub.stocktalkhub.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final JwtTokenizer jwtTokenizer;
    private final JwtTokenService jwtTokenService;
    private final RedisService redisService;
    private final BCryptPasswordEncoder passwordEncoder;



    @PostMapping("/user-module/members/login")
    public ResponseEntity login(@RequestBody MemberDTO member) {

        try {
            Member EntityMember = memberService.findByEmail(member.getEmail());
            memberService.login(member.getPassword(), EntityMember.getPassword());

            String accessToken = jwtTokenizer.createAccessToken(EntityMember.getId(), EntityMember.getEmail());
            String refreshToken = jwtTokenizer.createRefreshToken(EntityMember.getId(), EntityMember.getEmail());
            jwtTokenService.saveTokenRedis(accessToken, refreshToken, EntityMember);

            TokenDTO loginResponse = TokenDTO.builder()
                    .memberId(EntityMember.getId())
                    .email(EntityMember.getEmail())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

            Map<String, Object> response = new HashMap<>();
            response.put("message", "로그인에 성공하였습니다.");
            response.put("loginResponse", loginResponse);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (MemberService.EmailNotExistsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (MemberService.PasswordMismatchException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

//        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/user-module/members/emailCheck")
    public ResponseEntity<String> emailCheck(@RequestBody MemberDTO member) {
        try{
            memberService.sendCodeToEmail(member);

            String message = "의 메일로 인증번호가 전송되었습니다.";
            String response = "{\"message\": \"" +member.getEmail() + message + "\"}";
            return ResponseEntity.ok(response);
        } catch (MemberService.EmailAlreadyExistsException e){

//            이미 존재하는 이메일 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 이메일입니다.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/user-module/members/join")
    public ResponseEntity<String> join(@RequestBody MemberDTO member){

        try {
            Long id = memberService.join(member);

            String message = "회원가입에 성공하였습니다.";
            String response = "{\"message\": \"" + message + "\", \"memberId\": " + id + "}";
            return ResponseEntity.ok(response);
            // 인증 성공시 처리
        } catch (MemberService.ExpiredAuthCodeException e) {
            // 인증 코드 만료 오류 처리
            // 클라이언트에게 실패 메시지 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 코드가 만료되었습니다.");
        } catch (MemberService.InvalidAuthCodeException e) {
            // 인증 코드 일치 실패 오류 처리
            // 클라이언트에게 실패 메시지 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 코드가 일치하지 않습니다.");
        } catch (MemberService.EmailAlreadyExistsException e){
//            이미 존재하는 이메일 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 이메일입니다.");
        }
    }

    // 로그아웃
    @PostMapping("/user-module/members/logouts")
    public ResponseEntity logout(@AuthenticationPrincipal User user, @RequestBody TokenDTO tokenDTO){

        jwtTokenService.deleteRefreshToken(user);

        return ResponseEntity.ok("로그아웃에 성공하였습니다.");
    }


    // 업데이트
    @PutMapping("/user-module/members/{id}")
    public ResponseEntity update(@AuthenticationPrincipal User user, @RequestBody MemberDTO member,
                                 @PathVariable Long id){
        if (user.getId() != id){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유저가 일치하지 않습니다.");
        }

        memberService.MemberInfoUpdate(id, member);

        return ResponseEntity.ok("업데이트에 성공하였습니다.");
    }

    // 멤버 객체 불러오기
    @GetMapping("/user-module/members/{id}")
    public ResponseEntity findMember(@PathVariable Long id){

        Member members = memberService.findById(id);
        MemberDTO membersDTO = MemberDTO.builder()
                .name(members.getName())
                .introduce(members.getIntroduce())
                .profile_image(members.getProfileImage())
                .build();

        return ResponseEntity.ok().body(membersDTO);
    }

    @GetMapping("/user-module/members/")
    public ResponseEntity findAllmembers(){
        List<Member> members= memberService.findAllMembers();

        List<MemberDTO> memberDTOs = new ArrayList<>();
        for (Member member : members) {
            System.out.println(member.getId() + "아이디나오니?");
            MemberDTO memberDTO = MemberDTO.builder()
                    .id(member.getId())
                    .profile_image(member.getProfileImage())
                    .name(member.getName())
                    .introduce(member.getIntroduce())
                    .build();

            memberDTOs.add(memberDTO);
        }

        return ResponseEntity.ok().body(memberDTOs);
    }
    @PostMapping("/user-module/members/name")
    public ResponseEntity findNameAllmembers(@RequestBody MemberDTO requestMemberDTO){
        List<Member> members= memberService.findNameAllMembers(requestMemberDTO.getName());

        List<MemberDTO> memberDTOs = new ArrayList<>();

        for (Member member : members) {
            System.out.println(member.getId() + "아이디나오니?");
            MemberDTO memberDTO = MemberDTO.builder()
                    .id(member.getId())
                    .profile_image(member.getProfileImage())
                    .name(member.getName())
                    .introduce(member.getIntroduce())
                    .build();

            memberDTOs.add(memberDTO);
    }

        return ResponseEntity.ok().body(memberDTOs);
}








}
