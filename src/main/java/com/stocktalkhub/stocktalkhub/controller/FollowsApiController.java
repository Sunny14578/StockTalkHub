package com.stocktalkhub.stocktalkhub.controller;

import com.stocktalkhub.stocktalkhub.security.jwt.util.User;
import com.stocktalkhub.stocktalkhub.service.FollowsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FollowsApiController {

    private final FollowsService followsService;

    @PostMapping("follows/{id}")
    public ResponseEntity followsCreate(@AuthenticationPrincipal User user, @PathVariable Long id) {

        followsService.FollowsCreate(user.getId(), id);

        return ResponseEntity.status(HttpStatus.OK).body("팔로우 생성 완료");
    }

    @GetMapping("follows/{id}/posts")
    public ResponseEntity followsGetPost(@AuthenticationPrincipal User user, @PathVariable Long id){
        followsService.FollowsPostGet(id);
        return ResponseEntity.status(HttpStatus.OK).body("팔로우 포스트 불러오기 성공");
    }
}
