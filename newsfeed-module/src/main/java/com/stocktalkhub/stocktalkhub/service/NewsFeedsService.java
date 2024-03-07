package com.stocktalkhub.stocktalkhub.service;

import com.stocktalkhub.stocktalkhub.domain.NewsFeed;
import com.stocktalkhub.stocktalkhub.domain.NewsFeedType;
import com.stocktalkhub.stocktalkhub.dto.CommentDTO.NewsFeedByCommentsDTO;
import com.stocktalkhub.stocktalkhub.dto.FollowDTO.NewsFeedByFollowsDTO;
import com.stocktalkhub.stocktalkhub.dto.LikeDTO.NewsFeedByPostLikesDTO;
import com.stocktalkhub.stocktalkhub.dto.MemberDTO;
import com.stocktalkhub.stocktalkhub.dto.MessageWithData;
import com.stocktalkhub.stocktalkhub.dto.PostDTO.NewsFeedByPostsDTO;
import com.stocktalkhub.stocktalkhub.repository.NewsFeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class NewsFeedsService {

//    private final FollowsRepository followsRepository;
    private final NewsFeedRepository newsFeedRepository;
    private final LocalDateTime currentDateTime = LocalDateTime.now();
    private Map<Long, MemberDTO> idToMemberMap = new HashMap<>();
//    public List<MessageWithData> getFollowActivity(Long id) {
//        List<Long> following =  followsRepository.findFollowing(id).orElseThrow(() ->
//                new IllegalArgumentException("해당 팔로워가 존재하지 않습니다."));
//         팔로워 들을 찾아와야함
//        List<NewsFeed> newsFeeds = newsFeedRepository.findNewsFeedsBySenderIds(following).orElseThrow(() ->
//                new IllegalArgumentException("해당 뉴스피드가 존재하지 않습니다."));

//        return createNewsFeedMessages(newsFeeds);
//    }

    public List<MessageWithData> createNewsFeedMessages(List<NewsFeed> newsFeeds, List<MemberDTO> membersDTO) {
        List<String> messages = new ArrayList<>();
        List<MessageWithData> messagesWithDataList = new ArrayList<>();

        for (MemberDTO member : membersDTO){
            idToMemberMap.put(member.getId(), member);
        }

        for (NewsFeed newsFeed : newsFeeds) {

            MessageWithData messageWithData = createMessageWithData(newsFeed);
            messagesWithDataList.add(messageWithData);
        }

        return messagesWithDataList;
    }

    public MessageWithData createMessageWithData(NewsFeed newsFeed) {

        NewsFeedType type = newsFeed.getType();
        Long senderMemberId = newsFeed.getSenderId();
        Long receiverMemberId = newsFeed.getReceiverId();


        String customMessage = null;
        MessageWithData messagesWithData = null;
//        멤버 이름을 찾아와야함
        if (type.equals(NewsFeedType.POST)) {
            customMessage = idToMemberMap.get(senderMemberId).getName() + "님이 새로운 포스트를 공유했습니다.";


            messagesWithData = MessageWithData.builder()
                    .message(customMessage)
                    .postId(newsFeed.getPostId())
                    .timestamp(newsFeed.getTimestamp())
                    .build();
        } else if (type.equals(NewsFeedType.COMMENT)) {
            customMessage = idToMemberMap.get(senderMemberId).getName()  + "님이 " + idToMemberMap.get(receiverMemberId).getName() + "님의 글에 댓글을 남겼습니다.";

            messagesWithData = MessageWithData.builder()
                    .message(customMessage)
                    .commentId(newsFeed.getCommentId())
                    .timestamp(newsFeed.getTimestamp())
                    .build();

        } else if (type.equals(NewsFeedType.FOLLOW)){
            customMessage = idToMemberMap.get(senderMemberId).getName()  + "님이 " + idToMemberMap.get(receiverMemberId).getName() + "님을 팔로우 합니다.";

            messagesWithData = MessageWithData.builder()
                    .message(customMessage)
                    .followId(newsFeed.getFollowId())
                    .timestamp(newsFeed.getTimestamp())
                    .build();

        } else if (type.equals(NewsFeedType.COMMENTLIKE)){
            customMessage = idToMemberMap.get(senderMemberId).getName()  + "님이 " + idToMemberMap.get(receiverMemberId).getName() + "님의 댓글을 좋아합니다.";

            messagesWithData = MessageWithData.builder()
                    .message(customMessage)
                    .commentLikeId(newsFeed.getCommentLikeId())
                    .timestamp(newsFeed.getTimestamp())
                    .build();

        }else if (type.equals(NewsFeedType.POSTLIKE)){
            customMessage = idToMemberMap.get(senderMemberId).getName()  + "님이 " + idToMemberMap.get(receiverMemberId).getName() + "님의 글을 좋아합니다.";

            messagesWithData = MessageWithData.builder()
                    .message(customMessage)
                    .postLikeId(newsFeed.getPostLikeId())
                    .timestamp(newsFeed.getTimestamp())
                    .build();
        }
//        System.out.println(receiverMember + "898089098=== ");

        return messagesWithData;
    }

    public void createComments(Long id, NewsFeedByCommentsDTO requestCommentsDTO) {
        System.out.println(id + " " + requestCommentsDTO + "여기오나?");
        NewsFeed entityNewsFeed = NewsFeed.builder()
                .type(NewsFeedType.COMMENT)
                .commentId(requestCommentsDTO.getId())
                .senderId(id)
                .receiverId(requestCommentsDTO.getReceiverId())
                .timestamp(currentDateTime)
                .build();

        newsFeedRepository.save(entityNewsFeed);
    }

    public void createPosts(Long id, NewsFeedByPostsDTO requestPostsDTO) {
        NewsFeed entityNewsFeed = NewsFeed.builder()
                .type(NewsFeedType.POST)
                .postId(requestPostsDTO.getId())
                .senderId(id)
                .timestamp(currentDateTime)
                .build();

        newsFeedRepository.save(entityNewsFeed);
    }

    public void createFollows(Long id, NewsFeedByFollowsDTO requestFollowsDTO) {
        NewsFeed entityNewsFeed = NewsFeed.builder()
                .type(NewsFeedType.FOLLOW)
                .followId(requestFollowsDTO.getId())
                .senderId(id)
                .receiverId(requestFollowsDTO.getReceiverId())
                .timestamp(currentDateTime)
                .build();

        newsFeedRepository.save(entityNewsFeed);
    }

    public void createPostsLikes(Long id, NewsFeedByPostLikesDTO requestPostsLikesDTO) {
        NewsFeed entityNewsFeed = NewsFeed.builder()
                .type(NewsFeedType.POSTLIKE)
                .postLikeId(requestPostsLikesDTO.getId())
                .senderId(id)
                .receiverId(requestPostsLikesDTO.getReceiverId())
                .timestamp(currentDateTime)
                .build();

        newsFeedRepository.save(entityNewsFeed);
    }

    public void createCommentsLikes(Long id, NewsFeedByCommentsDTO requestCommentsLikesDTO) {
        NewsFeed entityNewsFeed = NewsFeed.builder()
                .type(NewsFeedType.COMMENTLIKE)
                .commentLikeId(requestCommentsLikesDTO.getId())
                .senderId(id)
                .receiverId(requestCommentsLikesDTO.getReceiverId())
                .timestamp(currentDateTime)
                .build();

        newsFeedRepository.save(entityNewsFeed);
    }

    public List<NewsFeed> getFollwingNewsFeeds(List<Long> followings) {

        List<NewsFeed> newsFeeds = newsFeedRepository.findNewsFeedsBySenderIds(followings).orElseThrow(() ->
                new IllegalArgumentException("뉴스피드 해당 팔로워가 존재하지 않습니다."));;

        return newsFeeds;
    }
}
