package com.stocktalkhub.stocktalkhub.controller;

import com.stocktalkhub.stocktalkhub.domain.Comment;
import com.stocktalkhub.stocktalkhub.domain.CommentLike;
import com.stocktalkhub.stocktalkhub.domain.Post;
import com.stocktalkhub.stocktalkhub.domain.PostLike;
import com.stocktalkhub.stocktalkhub.dto.LikeDTO.CommentLikesDTO;
import com.stocktalkhub.stocktalkhub.dto.LikeDTO.NewsFeedByCommentLikesDTO;
import com.stocktalkhub.stocktalkhub.dto.LikeDTO.NewsFeedByPostLikesDTO;
import com.stocktalkhub.stocktalkhub.dto.LikeDTO.PostLikesDTO;
import com.stocktalkhub.stocktalkhub.feign.ActivityFeignClient;
import com.stocktalkhub.stocktalkhub.service.CommentsService;
import com.stocktalkhub.stocktalkhub.service.LikesService;
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
public class LikesAPiController {
    private final LikesService likesService;
    private final PostsService postsService;
    private final CommentsService commentsService;
    private final ActivityFeignClient activityFeignClient;

    @PostMapping("posts/{id}/likes")
//    public ResponseEntity postsLikesCreate(@AuthenticationPrincipal User user, @PathVariable Long id) {
    public ResponseEntity postsLikesCreate(@PathVariable Long id, @RequestBody PostLikesDTO requestPostLikesDTO) {
//        PostLike postLike = likesService.postLikesCreate(user.getId(), id);
        Post posts = postsService.findPosts(requestPostLikesDTO.getPostId());
        PostLike postLike = likesService.postLikesCreate(id, posts);

        NewsFeedByPostLikesDTO nfBypLDTO = new NewsFeedByPostLikesDTO(postLike.getId(), posts.getMemberId());

        activityFeignClient.createNewsFeedPostLikes(id, nfBypLDTO);
//         뉴스피드에 전송
        return ResponseEntity.status(HttpStatus.OK).body("포스트 좋아요 완료");
    }

    @PostMapping("comments/{id}/likes")
//    public ResponseEntity commentsLikesCreate(@AuthenticationPrincipal User user, @PathVariable Long id) {
    public ResponseEntity commentsLikesCreate(@PathVariable Long id, @RequestBody CommentLikesDTO requestCommentLikesDTO) {

        Comment comments = commentsService.findComments(requestCommentLikesDTO.getCommentId());
        CommentLike commentLike = likesService.commentsLikesCreate(id, comments);
        NewsFeedByCommentLikesDTO nfBycLDTO = new NewsFeedByCommentLikesDTO(commentLike.getId(), comments.getMemberId());

        activityFeignClient.createNewsFeedCommentLikes(id, nfBycLDTO);
// 뉴스피드에 전송
        return ResponseEntity.status(HttpStatus.OK).body("댓글 좋아요 완료");
    }
}
