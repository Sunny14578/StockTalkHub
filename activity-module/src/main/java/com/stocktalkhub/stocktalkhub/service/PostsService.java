package com.stocktalkhub.stocktalkhub.service;

import com.stocktalkhub.stocktalkhub.domain.Post;
import com.stocktalkhub.stocktalkhub.dto.PostDTO.PostsDTO;
import com.stocktalkhub.stocktalkhub.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
//    private final MemberRepository memberRepository;
//    private final NewsFeedRepository newsFeedRepository;

    @Transactional
    public Post createPosts(Long id, PostsDTO posts) {

//        Member member = memberRepository.findOne(id).orElseThrow(() ->
//                new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        LocalDateTime timestamp = LocalDateTime.now();

        Post entityPost = Post.builder()
                .memberId(id)
                .content(posts.getContent())
                .createdAt(timestamp)
                .build();

        return postsRepository.save(entityPost);
    }

    @Transactional
    public Post findPosts(Long id){

        Post post = postsRepository.findOne(id).orElseThrow(() ->
                new IllegalArgumentException("해당 포스트가 존재하지 않습니다."));
        return post;
    }

    @Transactional
    public List<Post> findAllPosts(List<Long> followings){

        List<Post> posts = postsRepository.getFollowingsPosts(followings);

        return posts;
    }
}
