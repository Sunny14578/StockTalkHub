package com.stocktalkhub.stocktalkhub.controller;

import com.stocktalkhub.stocktalkhub.domain.Comment;
import com.stocktalkhub.stocktalkhub.domain.Post;
import com.stocktalkhub.stocktalkhub.dto.CommentDTO.CommentDTO;
import com.stocktalkhub.stocktalkhub.dto.CommentDTO.NewsFeedByCommentsDTO;
import com.stocktalkhub.stocktalkhub.feign.ActivityFeignClient;
import com.stocktalkhub.stocktalkhub.service.CommentsService;
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
public class CommentsApiController {
    private final CommentsService commentsService;
    private final PostsService postsService;
    private final ActivityFeignClient activityFeignClient;
    @PostMapping("comments/{id}")
    public ResponseEntity commentsCreate(@PathVariable Long id, @RequestBody CommentDTO requestCommentsDTO) {

        Comment comment = commentsService.createComments(id, requestCommentsDTO);
        Post post = postsService.findPosts(requestCommentsDTO.getPost_id());

        NewsFeedByCommentsDTO nfBycDTO = new NewsFeedByCommentsDTO(comment.getId(), post.getMemberId());
        activityFeignClient.createNewsFeedComment(id, nfBycDTO);
        // 뉴스피드에 전달되어야함
        return ResponseEntity.status(HttpStatus.OK).body("댓글 생성 완료");
    }
}
