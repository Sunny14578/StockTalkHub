package com.stocktalkhub.stocktalkhub.service;

import com.stocktalkhub.stocktalkhub.domain.Comment;
import com.stocktalkhub.stocktalkhub.domain.Member;
import com.stocktalkhub.stocktalkhub.domain.Post;
import com.stocktalkhub.stocktalkhub.dto.CommentDTO;
import com.stocktalkhub.stocktalkhub.repository.CommentsRepository;
import com.stocktalkhub.stocktalkhub.repository.MemberRepository;
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
    public void createComments(Long id, CommentDTO comments) {

        Member member = memberRepository.findOne(id).orElseThrow(() ->
                new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        Post post = postsRepository.findOne(comments.getPost_id()).orElseThrow(() ->
                new IllegalArgumentException("해당 포스트가 존재하지 않습니다."));

        Comment entityComments = Comment.builder()
                .member_id(member)
                .post_id(post)
                .content(comments.getContent())
                .created_at(LocalDateTime.now())
                .build();
        System.out.println(entityComments.getPostId() + "sdfdsfsd");

        commentsRepository.save(entityComments);
    }
}
