package com.stocktalkhub.stocktalkhub.repository;

import com.stocktalkhub.stocktalkhub.domain.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FollowsRepository {
    private final EntityManager em;

    public Follow save(Follow follows){
        em.persist(follows);
        System.out.print("성공");
        return follows;
    }

    public Optional<List<Long>> findFollowing(Long followerId) {
        String jpql = "SELECT f.followingId FROM Follow f WHERE f.followerId = :followerId";
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("followerId", followerId);
        return Optional.ofNullable(query.getResultList());
    }



}
