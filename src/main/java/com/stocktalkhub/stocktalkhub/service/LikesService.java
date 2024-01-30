package com.stocktalkhub.stocktalkhub.service;

import com.stocktalkhub.stocktalkhub.domain.*;
import com.stocktalkhub.stocktalkhub.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class LikesService {
    private final PostLikesRepository postLikesRepository;
    private final CommentsLikesRepository commentsLikesRepository;
    private final MemberRepository memberRepository;
    private final PostsRepository postsRepository;
    private final CommentsRepository commentsRepository;
    public void postLikesCreate(Long accessId, Long postId) {

        Member member = memberRepository.findOne(accessId).orElseThrow(() ->
                new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        Post post = postsRepository.findOne(postId).orElseThrow(() ->
                new IllegalArgumentException("해당 포스트가 존재하지 않습니다."));


        PostLike EntityPostLike = PostLike.builder()
                .memberId(member)
                .postId(post)
                .created_at(LocalDateTime.now())
                .build();


        postLikesRepository.save(EntityPostLike);
    }

    public void commentsLikesCreate(Long accessId, Long postId) {

        Member member = memberRepository.findOne(accessId).orElseThrow(() ->
                new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        Comment comment = commentsRepository.findOne(postId).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));


        CommentLike EntityCommentike = CommentLike.builder()
                .memberId(member)
                .commentId(comment)
                .created_at(LocalDateTime.now())
                .build();

        commentsLikesRepository.save(EntityCommentike);
    }
}
