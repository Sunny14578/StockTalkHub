package com.stocktalkhub.stocktalkhub.service;

import com.stocktalkhub.stocktalkhub.domain.Follow;
import com.stocktalkhub.stocktalkhub.domain.Post;
import com.stocktalkhub.stocktalkhub.repository.FollowsRepository;
import com.stocktalkhub.stocktalkhub.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class FollowsService {

    private final FollowsRepository followsRepository;
    private final PostsRepository postsRepository;

    public Follow FollowsCreate(Long accessId, Long followsId) {

//        Member follower = memberRepository.findOne(accessId).orElseThrow(() ->
//                new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));
//
//        Member following = memberRepository.findOne(followsId).orElseThrow(() ->
//                new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        Follow EntityFollows = Follow.builder()
                .followerId(accessId)
                .followingId(followsId)
                .build();

        return followsRepository.save(EntityFollows);

    }

    public List<Post> FollowsPostGet(Long id) {
//        Member follower = memberRepository.findOne(id).orElseThrow(() ->
//                new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        return postsRepository.getFollowsPosts(id);
    }
}
