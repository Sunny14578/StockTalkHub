package com.stocktalkhub.stocktalkhub.controller;

import com.stocktalkhub.stocktalkhub.domain.Follow;
import com.stocktalkhub.stocktalkhub.domain.Post;
import com.stocktalkhub.stocktalkhub.dto.FollowDTO.NewsFeedByFollowsDTO;
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

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FollowsApiController {

    private final FollowsService followsService;

//    팔로우 생성 API
    @PostMapping("follows/{id}")
    public ResponseEntity followsCreate(@AuthenticationPrincipal User user, @PathVariable Long id) {

        Follow follow = followsService.FollowsCreate(user.getId(), id);
        NewsFeedByFollowsDTO nfBycDTO = new NewsFeedByFollowsDTO(follow.getId());
        System.out.println(nfBycDTO + "팔로우 생성");
//        뉴스피드에 보내야함
        return ResponseEntity.status(HttpStatus.OK).body("팔로우 생성 완료");
    }

//     팔로우들의 포스트를 가져오는 API
    @GetMapping("follows/{id}/posts")
    public ResponseEntity followsGetPost(@AuthenticationPrincipal User user, @PathVariable Long id){

        List<Post> posts = followsService.FollowsPostGet(id);

        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }
}
