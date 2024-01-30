package com.stocktalkhub.stocktalkhub.repository;

import com.stocktalkhub.stocktalkhub.domain.PostLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PostLikesRepository {

    private final EntityManager em;
    public void save(PostLike postLike){
        em.persist(postLike);
        System.out.print("성공");
    }
}
