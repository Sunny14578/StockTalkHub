package com.stocktalkhub.stocktalkhub.repository;

import com.stocktalkhub.stocktalkhub.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
//    엔티티 매니저 주입
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
        System.out.print("성공");
    }

    public Optional<Member> findOne(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public Member findByEmail(String email){
        List<Member> resultList = em.createQuery("select m from Member m where m.email = :email", Member.class)
                                    .setParameter("email", email)
                                    .getResultList();

        if(resultList.isEmpty()) {
            System.out.print("결과");
            // 결과가 없는 경우 처리
            return null; // 또는 다른 방법으로 처리
        } else {
            // 결과가 있는 경우
            return resultList.get(0); // 첫 번째 결과 반환
        }
    }


    public List<Member> findAll() {
        List<Member> resultList = em.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();

        return resultList;
    }

    public List<Member> findByName(String name) {
        String jpql = "SELECT m FROM Member m WHERE m.name LIKE :namePrefix";
        TypedQuery<Member> query = em.createQuery(jpql, Member.class);
        query.setParameter("namePrefix", name + "%");
        return query.getResultList();
    }
}
