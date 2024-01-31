package com.stocktalkhub.stocktalkhub.service;

import com.stocktalkhub.stocktalkhub.domain.*;
import com.stocktalkhub.stocktalkhub.dto.CommentDTO;
import com.stocktalkhub.stocktalkhub.repository.CommentsRepository;
import com.stocktalkhub.stocktalkhub.repository.MemberRepository;
import com.stocktalkhub.stocktalkhub.repository.NewsFeedRepository;
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
    private final MemberRepository memberRepository;
    private final PostsRepository postsRepository;
    private final CommentsRepository commentsRepository;
    private final NewsFeedRepository newsFeedRepository;

    public void createComments(Long id, CommentDTO comments) {

        Member member = memberRepository.findOne(id).orElseThrow(() ->
                new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        Post post = postsRepository.findOne(comments.getPost_id()).orElseThrow(() ->
                new IllegalArgumentException("해당 포스트가 존재하지 않습니다."));


        LocalDateTime timestamp = LocalDateTime.now();

        Comment entityComments = Comment.builder()
                .member_id(member)
                .post_id(post)
                .content(comments.getContent())
                .created_at(timestamp)
                .build();

        NewsFeed entityNewsFeed = NewsFeed.builder()
                .comment(entityComments)
                .type(NewsFeedType.COMMENT)
                .timestamp(timestamp)
                .sender(member)
                .receiver(post.getMemberId())
                .build();

        commentsRepository.save(entityComments);
        newsFeedRepository.save(entityNewsFeed);
    }
}
