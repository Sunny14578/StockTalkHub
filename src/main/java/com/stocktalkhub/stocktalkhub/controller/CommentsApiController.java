package com.stocktalkhub.stocktalkhub.controller;

import com.stocktalkhub.stocktalkhub.dto.CommentDTO;
import com.stocktalkhub.stocktalkhub.service.CommentsService;
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
    @PostMapping("comments/{id}")
    public ResponseEntity commentsCreate(@PathVariable Long id, @RequestBody CommentDTO comments) {
        System.out.println(id + " " + comments);
        commentsService.createComments(id, comments);

        return ResponseEntity.status(HttpStatus.OK).body("댓글 생성 완료");
    }
}
