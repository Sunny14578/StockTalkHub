package com.stocktalkhub.stocktalkhub.feign;


//@FeignClient(url = "http://localhost:8080")

//    @RequestMapping(method = RequestMethod.GET, value = "")
//    List<ActivityMemberDTO>
// 연관관계가 설정이되있다보니까 'id 엔티티가 들어갔었다 연관이. 그러다보니까 멤버에서 엔티티를 가져오는 셀렉문이일어낫음
// but 모듈로나눠서 이제 엔티티를 넣는게아니라 ID값을 넣는다 그렇다면? 굳이 멤버를 요청할 이유가없지않나?
// 아! 그러면 멤버의 name필드를 가져오고싶기때문에 ID를이용해서 가져온다 갓잇갓잇

//}

import com.stocktalkhub.stocktalkhub.dto.CommentDTO.NewsFeedByCommentsDTO;
import com.stocktalkhub.stocktalkhub.dto.FollowDTO.NewsFeedByFollowsDTO;
import com.stocktalkhub.stocktalkhub.dto.LikeDTO.NewsFeedByCommentLikesDTO;
import com.stocktalkhub.stocktalkhub.dto.LikeDTO.NewsFeedByPostLikesDTO;
import com.stocktalkhub.stocktalkhub.dto.PostDTO.NewsFeedByPostsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "activityFeignClient", url = "http://localhost:8082")
public interface ActivityFeignClient {
    @PostMapping("/newsfeeds/comments/{id}")
    ResponseEntity<String> createNewsFeedComment(@PathVariable("id") Long id, @RequestBody NewsFeedByCommentsDTO nfBycDTO);

    @PostMapping("/newsfeeds/posts/{id}")
    ResponseEntity<String> createNewsFeedPost(@PathVariable("id") Long id, @RequestBody NewsFeedByPostsDTO nfBypDTO);

    @PostMapping("/newsfeeds/follows/{id}")
    ResponseEntity<String> createNewsFeedFollow(@PathVariable("id") Long id, NewsFeedByFollowsDTO nfByfDTO);

    @PostMapping("/newsfeeds/posts/{id}/likes")
    ResponseEntity<String> createNewsFeedPostLikes(@PathVariable("id") Long id, NewsFeedByPostLikesDTO nfBypLDTO);

    @PostMapping("/newsfeeds/comments/{id}/likes")
    ResponseEntity<String> createNewsFeedCommentLikes(@PathVariable("id") Long id, NewsFeedByCommentLikesDTO nfBycLDTO);
}