package com.stocktalkhub.stocktalkhub.controller;

import com.stocktalkhub.stocktalkhub.domain.CommentLike;
import com.stocktalkhub.stocktalkhub.domain.PostLike;
import com.stocktalkhub.stocktalkhub.dto.LikeDTO.NewsFeedByCommentLikesDTO;
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

        PostLike postLike = likesService.postLikesCreate(user.getId(), id);
        NewsFeedByCommentLikesDTO nfBypLDTO = new NewsFeedByCommentLikesDTO(postLike.getId());
        System.out.println(nfBypLDTO.toString() + "포스트좋아요");
//         뉴스피드에 전송
        return ResponseEntity.status(HttpStatus.OK).body("포스트 좋아요 완료");
    }

    @PostMapping("comments/{id}/likes")
    public ResponseEntity commentsLikesCreate(@AuthenticationPrincipal User user, @PathVariable Long id) {

        CommentLike commentLike = likesService.commentsLikesCreate(user.getId(), id);
        NewsFeedByCommentLikesDTO nfBycLDTO = new NewsFeedByCommentLikesDTO(commentLike.getId());
        System.out.println(nfBycLDTO.toString() + "댓글 좋아요");
// 뉴스피드에 전송
        return ResponseEntity.status(HttpStatus.OK).body("댓글 좋아요 완료");
    }
}
