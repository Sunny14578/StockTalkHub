package com.stocktalkhub.stocktalkhub.controller;

import com.stocktalkhub.stocktalkhub.domain.NewsFeed;
import com.stocktalkhub.stocktalkhub.dto.CommentDTO.NewsFeedByCommentsDTO;
import com.stocktalkhub.stocktalkhub.dto.FollowDTO.NewsFeedByFollowsDTO;
import com.stocktalkhub.stocktalkhub.dto.LikeDTO.NewsFeedByPostLikesDTO;
import com.stocktalkhub.stocktalkhub.dto.MemberDTO;
import com.stocktalkhub.stocktalkhub.dto.MessageWithData;
import com.stocktalkhub.stocktalkhub.dto.PostDTO.NewsFeedByPostsDTO;
import com.stocktalkhub.stocktalkhub.feign.NewsFeedToActivityFeignClient;
import com.stocktalkhub.stocktalkhub.feign.NewsFeedToMemberFeignClient;
import com.stocktalkhub.stocktalkhub.service.NewsFeedsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("newsfeed-module/")
public class NewsFeedApiController {

    private final NewsFeedsService newsFeedsService;
    private final NewsFeedToActivityFeignClient newsFeedToActivityFeignClient;
    private final NewsFeedToMemberFeignClient newsFeedToMemberFeignClient;

//    @GetMapping("newsfeeds/{id}")
//    public ResponseEntity<String> postsCreate(@PathVariable Long id) {
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
//        return ResponseEntity.status(HttpStatus.OK).body("뉴스피드");
//    }

    // todo
    @PostMapping("newsfeeds/comments/{id}")
    public ResponseEntity newsFeedsCommentsCreate(@PathVariable Long id, @RequestBody NewsFeedByCommentsDTO requestCommentsDTO){
        newsFeedsService.createComments(id, requestCommentsDTO);

        return ResponseEntity.status(HttpStatus.OK).body("댓글 뉴스피드에 생성");
    }

    @PostMapping("newsfeeds/posts/{id}")
    public ResponseEntity newsFeedsPostsCreate(@PathVariable Long id, @RequestBody NewsFeedByPostsDTO requestPostsDTO){
        newsFeedsService.createPosts(id, requestPostsDTO);

        return ResponseEntity.status(HttpStatus.OK).body("포스트 뉴스피드에 생성");
    }

    @PostMapping("newsfeeds/follows/{id}")
    public ResponseEntity newsFeedsPostsCreate(@PathVariable Long id, @RequestBody NewsFeedByFollowsDTO requestFollowsDTO){
        newsFeedsService.createFollows(id, requestFollowsDTO);

        return ResponseEntity.status(HttpStatus.OK).body("팔로우 뉴스피드에 생성");
    }

    @PostMapping("newsfeeds/posts/{id}/likes")
    public ResponseEntity newsFeedsPostsLikesCreate(@PathVariable Long id, @RequestBody NewsFeedByPostLikesDTO requestPostsLikesDTO){
        newsFeedsService.createPostsLikes(id, requestPostsLikesDTO);

        return ResponseEntity.status(HttpStatus.OK).body("팔로우 좋아요 뉴스피드에 생성");
    }

    @PostMapping("newsfeeds/comments/{id}/likes")
    public ResponseEntity newsFeedsCommentsLikesCreate(@PathVariable Long id, @RequestBody NewsFeedByCommentsDTO requestCommentsLikesDTO){System.out.println("여기오니?");
        newsFeedsService.createCommentsLikes(id, requestCommentsLikesDTO);
        return ResponseEntity.status(HttpStatus.OK).body("코멘트 좋아요 뉴스피드에 생성");
    }

    // 팔로우 가져오기
    @GetMapping("newsfeeds/{id}")
    public ResponseEntity newsFeedsGet(@PathVariable Long id){
//        ResponseEntity<String> members = newsFeedToMemberFeignClient.findMembers(id);
        ResponseEntity<List<MemberDTO>> responseMembers = newsFeedToMemberFeignClient.findAllMembers();

        ResponseEntity<List<Long>> response = newsFeedToActivityFeignClient.getFollwings(id);
        List<Long> followings = response.getBody();

        List<NewsFeed> newsFeeds = newsFeedsService.getFollwingNewsFeeds(followings);

        List<MessageWithData> responseData = newsFeedsService.createNewsFeedMessages(newsFeeds, responseMembers.getBody());

        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

}
