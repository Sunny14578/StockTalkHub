package com.stocktalkhub.stocktalkhub.service;

import com.stocktalkhub.stocktalkhub.domain.Post;
import com.stocktalkhub.stocktalkhub.dto.MemberDTO.MemberByNameDTO;
import com.stocktalkhub.stocktalkhub.dto.PostDTO.PostsDTO;
import com.stocktalkhub.stocktalkhub.feign.MemberFeignClient;
import com.stocktalkhub.stocktalkhub.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final MemberFeignClient memberFeignClient;

    @Transactional
    public Post createPosts(Long id, PostsDTO posts) {

        LocalDateTime timestamp = LocalDateTime.now();

        Post entityPost = Post.builder()
                .memberId(id)
                .title(posts.getTitle())
                .content(posts.getContent())
                .stockId(posts.getStockId())
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

    public List<Post> findAll(Long id) {
        List<Post> posts = postsRepository.getStocksPosts(id);
        return posts;
    }

    public List<Post> findFilterAll(Long filterId, String filter) {

        List<Post> posts = null;

        if (filterId != 1L) {
            posts = postsRepository.getStocksFilterPosts(filterId, filter);
        }else{
            MemberByNameDTO mfBynDTO = new MemberByNameDTO(filter);
            ResponseEntity<String> response = memberFeignClient.getNameMembers(mfBynDTO);
//            List<Map<String, Object>> responseBodyList = response.getBody();
            List<Long> idList = new ArrayList<>();

//            for (Map<String, Object> item : response) {
//                Long id = (Long) item.get("id");
//                idList.add(id);
            }

//            System.out.println(response.getBody() + "어떻게나올까");
//            List<MemberDTO> members = convertResponseToMemberDTOList(response.getBody());
//            posts = postsRepository.getStocksFilterIdPosts(filterId, filter);
        return posts;
    }
}
