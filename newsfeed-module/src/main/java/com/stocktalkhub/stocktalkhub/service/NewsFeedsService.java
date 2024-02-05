package com.stocktalkhub.stocktalkhub.service;

import com.stocktalkhub.stocktalkhub.domain.NewsFeed;
import com.stocktalkhub.stocktalkhub.domain.NewsFeedType;
import com.stocktalkhub.stocktalkhub.dto.CommentDTO.NewsFeedByCommentsDTO;
import com.stocktalkhub.stocktalkhub.dto.FollowDTO.NewsFeedByFollowsDTO;
import com.stocktalkhub.stocktalkhub.dto.LikeDTO.NewsFeedByPostLikesDTO;
import com.stocktalkhub.stocktalkhub.dto.MessageWithData;
import com.stocktalkhub.stocktalkhub.dto.PostDTO.NewsFeedByPostsDTO;
import com.stocktalkhub.stocktalkhub.repository.NewsFeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class NewsFeedsService {

//    private final FollowsRepository followsRepository;
    private final NewsFeedRepository newsFeedRepository;
    private final LocalDateTime currentDateTime = LocalDateTime.now();
//    public List<MessageWithData> getFollowActivity(Long id) {
//        List<Long> following =  followsRepository.findFollowing(id).orElseThrow(() ->
//                new IllegalArgumentException("해당 팔로워가 존재하지 않습니다."));
//         팔로워 들을 찾아와야함
//        List<NewsFeed> newsFeeds = newsFeedRepository.findNewsFeedsBySenderIds(following).orElseThrow(() ->
//                new IllegalArgumentException("해당 뉴스피드가 존재하지 않습니다."));

//        return createNewsFeedMessages(newsFeeds);
//    }

    private List<MessageWithData> createNewsFeedMessages(List<NewsFeed> newsFeeds) {
        List<String> messages = new ArrayList<>();
        List<MessageWithData> messagesWithDataList = new ArrayList<>();

        for (NewsFeed newsFeed : newsFeeds) {

            MessageWithData messageWithData = createMessageWithData(newsFeed);
            messagesWithDataList.add(messageWithData);
        }
        System.out.println("응답은 어떠니?");

        return messagesWithDataList;
    }

    public MessageWithData createMessageWithData(NewsFeed newsFeed) {
        NewsFeedType type = newsFeed.getType();
        System.out.println(type + "=== ");
        Long senderMember = newsFeed.getSenderId();
        System.out.println(senderMember + "123123=== ");
        Long receiverMember = newsFeed.getReceiverId();
        System.out.println(receiverMember + "456456=== ");

        String customMessage = null;
        MessageWithData messagesWithData = null;
//        멤버 이름을 찾아와야함
//        if (type.equals(NewsFeedType.POST)) {
//            customMessage = senderMember.getName() + "님이 새로운 포스트를 공유했습니다.";
//            PostsDTO p = PostsDTO.builder()
//                    .content("확인해보자")
//                    .build();
//            CommentDTO c = new CommentDTO(1L, "sdfds");
//
//            messagesWithData = MessageWithData.builder()
//                    .message(customMessage)
//                    .post(p)
//                    .comment(c)
//                    .build();
//        }
//        } else if (type.equals(NewsFeedType.COMMENT)) {
//            customMessage = senderMember.getName()  + "님이 " + receiverMember.getName() + "님의 글에 댓글을 남겼습니다.";
//
//            messagesWithData = MessageWithData.builder()
//                    .message(customMessage)
//                    .comment(newsFeed.getComment())
//                    .build();
//
//        } else if (type.equals(NewsFeedType.FOLLOW)){
//            customMessage = senderMember.getName()  + "님이 " + receiverMember.getName() + "님을 팔로우 합니다.";
//
//            messagesWithData = MessageWithData.builder()
//                    .message(customMessage)
//                    .post(newsFeed.getPost())
//                    .build();
//
//        } else if (type.equals(NewsFeedType.COMMENTLIKE)){
//            customMessage = senderMember.getName()  + "님이 " + receiverMember.getName() + "님의 댓글을 좋아합니다.";
//
//            messagesWithData = MessageWithData.builder()
//                    .message(customMessage)
//                    .build();
//
//        }else if (type.equals(NewsFeedType.POSTLIKE)){
//            customMessage = senderMember.getName()  + "님이 " + receiverMember.getName() + "님의 글을 좋아합니다.";
//
//            messagesWithData = MessageWithData.builder()
//                    .message(customMessage)
//                    .build();
//        }
        System.out.println(receiverMember + "898089098=== ");

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
                .postLikeId(requestCommentsLikesDTO.getId())
                .senderId(id)
                .receiverId(requestCommentsLikesDTO.getReceiverId())
                .timestamp(currentDateTime)
                .build();

        newsFeedRepository.save(entityNewsFeed);
    }
}
