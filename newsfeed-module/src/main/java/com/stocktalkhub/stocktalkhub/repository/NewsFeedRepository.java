package com.stocktalkhub.stocktalkhub.repository;

import com.stocktalkhub.stocktalkhub.domain.NewsFeed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NewsFeedRepository {

    private final EntityManager em;

    public void save(NewsFeed newsFeed){
        em.persist(newsFeed);
        System.out.print("성공");
    }

    public Optional<List<NewsFeed>> findNewsFeedsBySenderIds(List<Long> follwoingsIds) {
        String jpql = "SELECT nf FROM NewsFeed nf " +
                "WHERE nf.senderId IN :senderIds";
        TypedQuery<NewsFeed> query = em.createQuery(jpql, NewsFeed.class);
        query.setParameter("senderIds", follwoingsIds);
        List<NewsFeed> newsFeeds = query.getResultList();
        return Optional.ofNullable(newsFeeds);
    }
}
