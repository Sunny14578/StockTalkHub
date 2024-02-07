package com.stocktalkhub.stocktalkhub.feign;


import com.stocktalkhub.stocktalkhub.dto.MemberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "newsToMemberFeignClient", url = "http://localhost:8080/user-module")
public interface NewsFeedToMemberFeignClient {
    @GetMapping("/members/{id}")
    ResponseEntity<String> findMembers(@PathVariable("id") Long id);

    @GetMapping("/members/")
    ResponseEntity<List<MemberDTO>> findAllMembers();

//    @PostMapping("/newsfeeds/posts/{id}")
//    ResponseEntity<String> createNewsFeedPost(@PathVariable("id") Long id, @RequestBody NewsFeedByPostsDTO nfBypDTO);


}