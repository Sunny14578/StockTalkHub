package com.stocktalkhub.stocktalkhub.repository;

import com.stocktalkhub.stocktalkhub.domain.CommentLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CommentsLikesRepository {
    private final EntityManager em;
    public CommentLike save(CommentLike commentLike){
        em.persist(commentLike);
        System.out.print("성공");
        return commentLike;
    }
}
