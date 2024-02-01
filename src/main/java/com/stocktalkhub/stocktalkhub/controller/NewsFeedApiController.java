package com.stocktalkhub.stocktalkhub.controller;

import com.stocktalkhub.stocktalkhub.service.NewsFeedsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
//    @PostMapping("newsfeeds/comments")
//    public ResponseEntity newsFeedsCommentsCreate(@PathVariable Long id){
//        return
//    }
}
