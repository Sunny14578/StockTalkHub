package com.stocktalkhub.stocktalkhub.repository;

import com.stocktalkhub.stocktalkhub.domain.Member;
import com.stocktalkhub.stocktalkhub.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostsRepository {

    private final EntityManager em;
    public void save(Post post){
        em.persist(post);
        System.out.print("성공");
    }

    public List<Post> getPostsById(Long id) {
        String jpql = "SELECT p FROM Post p WHERE p.id = :id";
        TypedQuery<Post> query = em.createQuery(jpql, Post.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public Optional<Post> findOne(Long id) {
        Post post = em.find(Post.class, id);
        return Optional.ofNullable(post);
    }

    public List<Post> getFollowsPosts(Member member) {
        String jpql = "SELECT p FROM Post p " +
//                                "JOIN p.memberId m " +
//                                "JOIN m.following f " +
                                "WHERE f.id = :memberId";
        TypedQuery<Post> query = em.createQuery(jpql, Post.class);
        query.setParameter("memberId", member.getId());
        return query.getResultList();
    }
}