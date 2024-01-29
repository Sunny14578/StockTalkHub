package com.stocktalkhub.stocktalkhub.repository;

import com.stocktalkhub.stocktalkhub.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CommentsRepository {
    private final EntityManager em;
    public void save(Comment comments){
        em.persist(comments);
        System.out.print("성공");
    }
}
