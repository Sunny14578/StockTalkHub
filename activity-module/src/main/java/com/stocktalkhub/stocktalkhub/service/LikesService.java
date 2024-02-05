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
    private final PostsRepository postsRepository;
    private final CommentsRepository commentsRepository;

    public PostLike postLikesCreate(Long accessId, Post posts) {

//        Member member = memberRepository.findOne(accessId).orElseThrow(() ->
//                new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

//        Post post = postsRepository.findOne(postId).orElseThrow(() ->
//                new IllegalArgumentException("해당 포스트가 존재하지 않습니다."));

        PostLike EntityPostLike = PostLike.builder()
                .memberId(accessId)
                .post(posts)
                .createdAt(LocalDateTime.now())
                .build();

//        NewsFeed entityNewsFeed = NewsFeed.builder()
//                .type(NewsFeedType.POSTLIKE)
//                .postLike(EntityPostLike)
//                .timestamp(LocalDateTime.now())
//                .sender(member)
//                .receiver(post.getMemberId())
//                .build();

        return postLikesRepository.save(EntityPostLike);
//        newsFeedRepository.save(entityNewsFeed);
    }

        public CommentLike commentsLikesCreate(Long accessId, Comment comments) {

//        Member member = memberRepository.findOne(accessId).orElseThrow(() ->
//                new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

//        Comment comment = commentsRepository.findOne(postId).orElseThrow(() ->
//                new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));


        CommentLike EntityCommentike = CommentLike.builder()
                .memberId(accessId)
                .comment(comments)
                .createdAt(LocalDateTime.now())
                .build();

//        NewsFeed entityNewsFeed = NewsFeed.builder()
//                .type(NewsFeedType.COMMENTLIKE)
//                .commentLike(EntityCommentike)
//                .timestamp(LocalDateTime.now())
//                .sender(member)
//                .receiver(comment.getMemberId())
//                .build();

        return commentsLikesRepository.save(EntityCommentike);
//        newsFeedRepository.save(entityNewsFeed);

    }
}
