package com.stocktalkhub.stocktalkhub.controller;

import com.stocktalkhub.stocktalkhub.domain.Post;
import com.stocktalkhub.stocktalkhub.dto.PostDTO.NewsFeedByPostsDTO;
import com.stocktalkhub.stocktalkhub.dto.PostDTO.PostsDTO;
import com.stocktalkhub.stocktalkhub.feign.ActivityFeignClient;
import com.stocktalkhub.stocktalkhub.service.PostsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("activity-module/")
public class PostsApiController {

    private final PostsService postsService;
    private final ActivityFeignClient activityFeignClient;

    @Transactional
    @PostMapping("posts/{id}")
    public ResponseEntity postsCreate(@PathVariable Long id, @RequestBody PostsDTO posts) {

        Post post = postsService.createPosts(id, posts);
//       뉴스피드에 보내야함
        NewsFeedByPostsDTO nfBypDTO = new NewsFeedByPostsDTO(id, post.getId());

        activityFeignClient.createNewsFeedPost(id, nfBypDTO);
        return ResponseEntity.status(HttpStatus.OK).body("포스트 생성 완료");
    }

    @GetMapping("resilience4jCheck")
    public void resilience4jCheck(){
        activityFeignClient.resilience4jCheck();
//        return ResponseEntity.status(HttpStatus.OK).body("경과확인");
    }

}
