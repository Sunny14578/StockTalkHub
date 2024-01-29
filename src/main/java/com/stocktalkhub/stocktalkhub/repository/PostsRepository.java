package com.stocktalkhub.stocktalkhub.repository;

import com.stocktalkhub.stocktalkhub.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

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
}
