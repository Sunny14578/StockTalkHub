package com.stocktalkhub.stocktalkhub.controller;

import com.stocktalkhub.stocktalkhub.dto.PostsDTO;
import com.stocktalkhub.stocktalkhub.service.PostsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("posts/{id}")
    public ResponseEntity postsCreate(@PathVariable Long id, @RequestBody PostsDTO posts) {
        postsService.createPosts(id, posts);

        return ResponseEntity.status(HttpStatus.OK).body("포스트 생성 완료");
    }

}
