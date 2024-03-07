package com.stocktalkhub.stocktalkhub.controller;

import com.stocktalkhub.stocktalkhub.domain.Follow;
import com.stocktalkhub.stocktalkhub.domain.Post;
import com.stocktalkhub.stocktalkhub.dto.FollowDTO.FollowsDTO;
import com.stocktalkhub.stocktalkhub.dto.FollowDTO.NewsFeedByFollowsDTO;
import com.stocktalkhub.stocktalkhub.feign.ActivityFeignClient;
import com.stocktalkhub.stocktalkhub.service.FollowsService;
import com.stocktalkhub.stocktalkhub.service.PostsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("activity-module/")
public class FollowsApiController {

    private final FollowsService followsService;
    private final PostsService postsService;
    private final ActivityFeignClient activityFeignClient;

//    팔로우 생성 API
    @PostMapping("follows/{id}")
//    public ResponseEntity followsCreate(@AuthenticationPrincipal User user, @PathVariable Long id) {
    public ResponseEntity followsCreate(@PathVariable Long id, @RequestBody FollowsDTO requestFollowDTO) {

//        Follow follow = followsService.FollowsCreate(user.getId(), id);
        Follow follow = followsService.FollowsCreate(id, requestFollowDTO.getReceiverId());
        NewsFeedByFollowsDTO nfByfDTO = new NewsFeedByFollowsDTO(follow.getId(), requestFollowDTO.getReceiverId());

        activityFeignClient.createNewsFeedFollow(id, nfByfDTO);
//        뉴스피드에 보내야함
        return ResponseEntity.status(HttpStatus.OK).body("팔로우 생성 완료");
    }

//     팔로우들의 포스트를 가져오는 API
    @GetMapping("follows/{id}/posts")
//    public ResponseEntity followsGetPost(@AuthenticationPrincipal User user, @PathVariable Long id){
    public ResponseEntity followsGetPost(@PathVariable Long id){
        List<Long> follows = followsService.findfollowing(id);
        List<Post> posts = postsService.findAllPosts(follows);

        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

//     팔로잉들 가져오기
    @GetMapping("follows/{id}")
    public ResponseEntity getFollwings(@PathVariable Long id){
        List<Long> follows = followsService.findfollowing(id);

        return ResponseEntity.status(HttpStatus.OK).body(follows);
    }
}
