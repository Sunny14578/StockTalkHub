package com.stocktalkhub.stocktalkhub.controller;

import com.stocktalkhub.stocktalkhub.security.jwt.util.User;
import com.stocktalkhub.stocktalkhub.service.LikesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LikesAPiController {
    private final LikesService likesService;
    @PostMapping("posts/{id}/likes")
    public ResponseEntity postsLikesCreate(@AuthenticationPrincipal User user, @PathVariable Long id) {

        likesService.postLikesCreate(user.getId(), id);

        return ResponseEntity.status(HttpStatus.OK).body("포스트 좋아요 완료");
    }

    @PostMapping("comments/{id}/likes")
    public ResponseEntity commentsLikesCreate(@AuthenticationPrincipal User user, @PathVariable Long id) {

        likesService.commentsLikesCreate(user.getId(), id);

        return ResponseEntity.status(HttpStatus.OK).body("댓글 좋아요 완료");
    }
}
