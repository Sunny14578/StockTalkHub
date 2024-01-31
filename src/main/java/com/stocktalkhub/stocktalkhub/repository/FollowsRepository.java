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

    public void save(Follow Follows){
        em.persist(Follows);
        System.out.print("성공");
    }

    public Optional<List<Long>> findFollowing(Long followerId) {
        String jpql = "SELECT f.following.id FROM Follow f WHERE f.follower.id = :followerId";
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("followerId", followerId);
        return Optional.ofNullable(query.getResultList());
    }



}
