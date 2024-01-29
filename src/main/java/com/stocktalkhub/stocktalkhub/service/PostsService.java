package com.stocktalkhub.stocktalkhub.service;

import com.stocktalkhub.stocktalkhub.domain.Member;
import com.stocktalkhub.stocktalkhub.domain.Post;
import com.stocktalkhub.stocktalkhub.dto.PostsDTO;
import com.stocktalkhub.stocktalkhub.repository.MemberRepository;
import com.stocktalkhub.stocktalkhub.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createPosts(Long id, PostsDTO posts) {

        Member member = memberRepository.findOne(id).orElseThrow(() ->
                new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        Post entityPost = Post.builder()
                .member_id(member)
                .content(posts.getContent())
                .created_at(LocalDateTime.now())
                .build();

        postsRepository.save(entityPost);
    }
}
