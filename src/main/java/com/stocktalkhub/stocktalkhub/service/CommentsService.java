package com.stocktalkhub.stocktalkhub.service;

import com.stocktalkhub.stocktalkhub.domain.Comment;
import com.stocktalkhub.stocktalkhub.domain.Post;
import com.stocktalkhub.stocktalkhub.dto.CommentDTO.CommentDTO;
import com.stocktalkhub.stocktalkhub.repository.CommentsRepository;
import com.stocktalkhub.stocktalkhub.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class CommentsService {

    private final PostsRepository postsRepository;
    private final CommentsRepository commentsRepository;

    public Comment createComments(Long id, CommentDTO comments) {
        Post post = postsRepository.findOne(comments.getPost_id()).orElseThrow(() ->
                new IllegalArgumentException("해당 포스트가 존재하지 않습니다."));

        LocalDateTime timestamp = LocalDateTime.now();

        Comment entityComments = Comment.builder()
                .memberId(id)
                .post(post)
                .content(comments.getContent())
                .createdAt(timestamp)
                .build();

        return commentsRepository.save(entityComments);
    }
}
