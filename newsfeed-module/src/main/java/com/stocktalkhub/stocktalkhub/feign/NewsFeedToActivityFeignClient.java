package com.stocktalkhub.stocktalkhub.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "newsToActivityFeignClient", url = "http://localhost:8081")
public interface NewsFeedToActivityFeignClient {
    @GetMapping("follows/{id}")
    ResponseEntity<List<Long>> getFollwings(@PathVariable("id") Long id);




//    @PostMapping("/newsfeeds/posts/{id}")
//    ResponseEntity<String> createNewsFeedPost(@PathVariable("id") Long id, @RequestBody NewsFeedByPostsDTO nfBypDTO);


}