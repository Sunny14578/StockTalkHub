package com.stocktalkhub.stocktalkhub.controller;

import com.stocktalkhub.stocktalkhub.dto.CommentDTO.NewsFeedByCommentsDTO;
import com.stocktalkhub.stocktalkhub.dto.FollowDTO.NewsFeedByFollowsDTO;
import com.stocktalkhub.stocktalkhub.dto.LikeDTO.NewsFeedByPostLikesDTO;
import com.stocktalkhub.stocktalkhub.dto.PostDTO.NewsFeedByPostsDTO;
import com.stocktalkhub.stocktalkhub.service.NewsFeedsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NewsFeedApiController {

    private final NewsFeedsService newsFeedsService;
    @GetMapping("newsfeeds/{id}")
    public ResponseEntity<String> postsCreate(@PathVariable Long id) {
//        List<MessageWithData> newsFeeds = newsFeedsService.getFollowActivity(id);
//        for (MessageWithData f : newsFeeds) {
//
//            System.out.println(f.getMessage());
//            System.out.println(f.getComment());
//            System.out.println(f.getPost());
//        }
//
//        List<MessageWithData> n = new ArrayList<>();
//        MessageWithData messagesWithData = MessageWithData.builder()
//                .message("sdfsdf")
//                .post(Post post = new ())
//                .comment()
//                .build();

//        n.add(new MessageWithData("sdf", "jjj", "kkk"));
        return ResponseEntity.status(HttpStatus.OK).body("뉴스피드");
    }

    // todo
    @PostMapping("newsfeeds/comments/{id}")
    public ResponseEntity newsFeedsCommentsCreate(@PathVariable Long id, @RequestBody NewsFeedByCommentsDTO requestCommentsDTO){
        System.out.println(id +"  "+requestCommentsDTO);
        newsFeedsService.createComments(id, requestCommentsDTO);
        System.out.print("생성완료");

        return ResponseEntity.status(HttpStatus.OK).body("댓글 뉴스피드에 생성");
    }

    @PostMapping("newsfeeds/posts/{id}")
    public ResponseEntity newsFeedsPostsCreate(@PathVariable Long id, @RequestBody NewsFeedByPostsDTO requestPostsDTO){
        System.out.println(id +"  "+requestPostsDTO);
        newsFeedsService.createPosts(id, requestPostsDTO);
        System.out.print("생성완료");

        return ResponseEntity.status(HttpStatus.OK).body("포스트 뉴스피드에 생성");
    }

    @PostMapping("newsfeeds/follows/{id}")
    public ResponseEntity newsFeedsPostsCreate(@PathVariable Long id, @RequestBody NewsFeedByFollowsDTO requestFollowsDTO){
        System.out.println(id +"  "+requestFollowsDTO);
        newsFeedsService.createFollows(id, requestFollowsDTO);
        System.out.print("생성완료");

        return ResponseEntity.status(HttpStatus.OK).body("팔로우 뉴스피드에 생성");
    }

    @PostMapping("newsfeeds/posts/{id}/likes")
    public ResponseEntity newsFeedsPostsLikesCreate(@PathVariable Long id, @RequestBody NewsFeedByPostLikesDTO requestPostsLikesDTO){
        newsFeedsService.createPostsLikes(id, requestPostsLikesDTO);

        return ResponseEntity.status(HttpStatus.OK).body("팔로우 좋아요 뉴스피드에 생성");
    }

    @PostMapping("newsfeeds/comments/{id}/likes")
    public ResponseEntity newsFeedsCommentsLikesCreate(@PathVariable Long id, @RequestBody NewsFeedByCommentsDTO requestCommentsLikesDTO){
        System.out.println("여기오니?");
        newsFeedsService.createCommentsLikes(id, requestCommentsLikesDTO);
        return ResponseEntity.status(HttpStatus.OK).body("코멘트 좋아요 뉴스피드에 생성");
    }
}
