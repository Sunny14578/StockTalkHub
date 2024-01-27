package com.stocktalkhub.stocktalkhub.repository;

import com.stocktalkhub.stocktalkhub.domain.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
//    엔티티 매니저 주입
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
        System.out.print("성공");
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
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

}
