package com.stocktalkhub.stocktalkhub;

import com.stocktalkhub.stocktalkhub.domain.Member;
import com.stocktalkhub.stocktalkhub.dto.MemberDTO;
import com.stocktalkhub.stocktalkhub.repository.MemberRepository;
import com.stocktalkhub.stocktalkhub.service.MemberService;
import com.stocktalkhub.stocktalkhub.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @MockBean
    RedisService redisService;

    @Autowired
    EntityManager em;

    @Test
    public void join() throws Exception{

        //given
        MemberDTO member = new MemberDTO();
        member.setAuth("Authcode");
        member.setEmail("example@example.com");
        member.setName("Choi taeyang");
        member.setPassword("password");

        when(redisService.getValues(anyString())).thenReturn("Authcode");
        when(redisService.checkExistsValue(anyString())).thenReturn(true);

        //when
        Long savedId = memberService.join(member);

        //then
        Optional<Member> memberResponse = memberRepository.findOne(savedId);
        assertEquals(member.getEmail(), memberResponse.get().getEmail());
    }
}
