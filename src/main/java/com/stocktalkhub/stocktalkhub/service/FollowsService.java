package com.stocktalkhub.stocktalkhub.service;

import com.stocktalkhub.stocktalkhub.domain.Follow;
import com.stocktalkhub.stocktalkhub.domain.Member;
import com.stocktalkhub.stocktalkhub.repository.FollowsRepository;
import com.stocktalkhub.stocktalkhub.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class FollowsService {
    private final MemberRepository memberRepository;
    private final FollowsRepository followsRepository;
    public void FollowsCreate(Long accessId, Long followsId) {

        Member follower = memberRepository.findOne(accessId).orElseThrow(() ->
                new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        Member following = memberRepository.findOne(followsId).orElseThrow(() ->
                new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        Follow EntityFollows = Follow.builder()
                .follower(follower)
                .following(following)
                .build();

        followsRepository.save(EntityFollows);
    }

    public void FollowsPostGet(Long id) {
        Member follower = memberRepository.findOne(id).orElseThrow(() ->
                new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));


    }
}
