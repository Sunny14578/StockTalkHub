package com.stocktalkhub.stocktalkhub.controller;

import com.stocktalkhub.stocktalkhub.dto.MemberDTO;
import com.stocktalkhub.stocktalkhub.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping({"", "/"})
    public String index() {
        // 머스테치 기본폴더 src/main/resources/
        // 뷰리졸버 설정 : templates (prefix), .mustache (suffix) 의존성에 추가됐다면 자동으로 설정됨 생략가능
        return "index"; // src/main/resources/templates/index.mustache
    }

    @GetMapping("user")
    public @ResponseBody String user(){
        return "admin";
    }

    @GetMapping("manager")
    public @ResponseBody String manager(){
        return "manager";
    }

    @PostMapping("/login")
    public String loginForm(@RequestBody MemberDTO member) {
        return member.toString();
    }

    @PostMapping("/emailCheck")
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

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody MemberDTO member){
        System.out.println(member);

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









}
