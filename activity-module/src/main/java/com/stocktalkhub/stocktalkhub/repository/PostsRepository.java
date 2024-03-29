package com.stocktalkhub.stocktalkhub.repository;

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
    public Post save(Post post){
        em.persist(post);
        System.out.print("성공");
        return post;
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

    public List<Post> getFollowsPosts(Long memberId) {
        String jpql = "SELECT p FROM Post p " +
                                "WHERE p.memberId = :memberId";
        TypedQuery<Post> query = em.createQuery(jpql, Post.class);
        query.setParameter("memberId", memberId);
        return query.getResultList();
    }

    public  List<Post> getFollowingsPosts(List<Long> followingIds){
        String jpql = "SELECT p FROM Post p WHERE p.member.id IN :followingIds";
        TypedQuery<Post> query = em.createQuery(jpql, Post.class);
        query.setParameter("followingIds", followingIds);
        return query.getResultList();
    }

    public List<Post> getStocksPosts(Long id) {
        String jpql = "SELECT p FROM Post p " +
                "WHERE p.stockId = :stockId";
        TypedQuery<Post> query = em.createQuery(jpql, Post.class);
        query.setParameter("stockId", id);
        return query.getResultList();
    }

    public List<Post> getStocksFilterPosts(Long filterId, String filter) {
        String jpql = "SELECT p FROM Post p ";
        System.out.println(filter + "제목뭐가오니");
        if (filterId == 0) {
            jpql += "WHERE p.title LIKE CONCAT('%', :filter, '%')";
        } else if (filterId == 2) {
            jpql += "WHERE p.content LIKE CONCAT('%', :filter, '%')";
        } else {
            throw new IllegalArgumentException("Invalid filterId: " + filterId);
        }

        TypedQuery<Post> query = em.createQuery(jpql, Post.class);
        query.setParameter("filter", filter);

        return query.getResultList();
    }
}
