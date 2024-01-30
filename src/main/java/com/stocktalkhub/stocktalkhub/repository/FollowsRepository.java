package com.stocktalkhub.stocktalkhub.repository;

import com.stocktalkhub.stocktalkhub.domain.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class FollowsRepository {
    private final EntityManager em;

    public void save(Follow Follows){
        em.persist(Follows);
        System.out.print("성공");
    }

}
