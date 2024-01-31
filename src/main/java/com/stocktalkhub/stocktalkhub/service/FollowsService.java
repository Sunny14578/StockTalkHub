package com.stocktalkhub.stocktalkhub.service;

import com.stocktalkhub.stocktalkhub.domain.*;
import com.stocktalkhub.stocktalkhub.repository.FollowsRepository;
import com.stocktalkhub.stocktalkhub.repository.MemberRepository;
import com.stocktalkhub.stocktalkhub.repository.NewsFeedRepository;
import com.stocktalkhub.stocktalkhub.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class FollowsService {
    private final MemberRepository memberRepository;
    private final FollowsRepository followsRepository;
    private final PostsRepository postsRepository;
    private final NewsFeedRepository newsFeedRepository;
    public void FollowsCreate(Long accessId, Long followsId) {

        Member follower = memberRepository.findOne(accessId).orElseThrow(() ->
                new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        Member following = memberRepository.findOne(followsId).orElseThrow(() ->
                new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        Follow EntityFollows = Follow.builder()
                .follower(follower)
                .following(following)
                .build();

        NewsFeed entityNewsFeed = NewsFeed.builder()
                .type(NewsFeedType.FOLLOW)
                .follow(EntityFollows)
                .timestamp(LocalDateTime.now())
                .sender(follower)
                .receiver(following)
                .build();

        followsRepository.save(EntityFollows);
        newsFeedRepository.save(entityNewsFeed);
    }

    public List<Post> FollowsPostGet(Long id) {
        Member follower = memberRepository.findOne(id).orElseThrow(() ->
                new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        return postsRepository.getFollowsPosts(follower);
    }
}
