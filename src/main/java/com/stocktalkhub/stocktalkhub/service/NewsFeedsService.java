package com.stocktalkhub.stocktalkhub.service;

import com.stocktalkhub.stocktalkhub.domain.Member;
import com.stocktalkhub.stocktalkhub.domain.NewsFeed;
import com.stocktalkhub.stocktalkhub.domain.NewsFeedType;
import com.stocktalkhub.stocktalkhub.dto.CommentDTO;
import com.stocktalkhub.stocktalkhub.dto.MessageWithData;
import com.stocktalkhub.stocktalkhub.dto.PostsDTO;
import com.stocktalkhub.stocktalkhub.repository.FollowsRepository;
import com.stocktalkhub.stocktalkhub.repository.NewsFeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class NewsFeedsService {

    private final FollowsRepository followsRepository;
    private final NewsFeedRepository newsFeedRepository;
    public List<MessageWithData> getFollowActivity(Long id) {
        List<Long> following =  followsRepository.findFollowing(id).orElseThrow(() ->
                new IllegalArgumentException("해당 팔로워가 존재하지 않습니다."));

        List<NewsFeed> newsFeeds = newsFeedRepository.findNewsFeedsBySenderIds(following).orElseThrow(() ->
                new IllegalArgumentException("해당 뉴스피드가 존재하지 않습니다."));

        return createNewsFeedMessages(newsFeeds);
    }

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
        Member senderMember = newsFeed.getSender();
        System.out.println(senderMember + "123123=== ");
        Member receiverMember = newsFeed.getReceiver();
        System.out.println(receiverMember + "456456=== ");

        String customMessage = null;
        MessageWithData messagesWithData = null;

        if (type.equals(NewsFeedType.POST)) {
            customMessage = senderMember.getName() + "님이 새로운 포스트를 공유했습니다.";
            PostsDTO p = PostsDTO.builder()
                    .content("확인해보자")
                    .build();
            CommentDTO c = new CommentDTO(1L, "sdfds");

            messagesWithData = MessageWithData.builder()
                    .message(customMessage)
                    .post(p)
                    .comment(c)
                    .build();
        }
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

}
