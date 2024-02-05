package com.stocktalkhub.stocktalkhub.security.jwt.filter;

import com.stocktalkhub.stocktalkhub.domain.Member;
import com.stocktalkhub.stocktalkhub.repository.MemberRepository;
import com.stocktalkhub.stocktalkhub.security.jwt.util.JwtTokenizer;
import com.stocktalkhub.stocktalkhub.security.jwt.util.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;
    private final JwtTokenizer jwtTokenizer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

// log.info("jwtTokenFilter 의 doFilterInternal() 실행 = 인증 필터");



        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.print(header);
//        System.out.print(header.startsWith("Bearer "));
        if (header == null || !header.startsWith("Bearer ")) {
            log.error("Error occurs while getting header. header is null or invalid");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = header.split(" ")[1].trim();

//            if (JwtTokenUtils.isExpired(token, key)) {
//                log.error("Key is expired");
//                filterChain.doFilter(request, response);
//                return;
//            }

            Long memberId = jwtTokenizer.getUserIdFromToken(token);
            Member member = memberRepository.findOne(memberId).orElseThrow(() ->
                    new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

//            if (member == null){
//                filterChain.doFilter(request, response);
//                return;
//            }

            User user = new User(member.getId(), member.getEmail(), member.getPassword(), Set.of());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                   user , null, user.getAuthorities()
            );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication); //-> 컨트롤러로 Authentiaction을 보내준다.

        } catch (RuntimeException e ) {
            log.error("Error occurs while validating. {}", e.toString());
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
