package org.example.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jwt.util.JwtTokenizer;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    private final JwtTokenizer jwtTokenizer;

    public CustomFilter(JwtTokenizer jwtTokenizer) {
        super(Config.class);
        this.jwtTokenizer = jwtTokenizer;
    }

    @Override
    public GatewayFilter apply(Config config) {


        return (exchange, chain) -> {
            log.error("gggggg");
            ServerHttpRequest request = exchange.getRequest();

            HttpHeaders header = request.getHeaders();
            String auth = header.getFirst("authorization");
            log.error(auth);

            if (auth == null || !auth.startsWith("Bearer ")) {

                log.error("Error occurs while getting header. header is null or invalid");

                exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                return exchange.getResponse().setComplete();
            }

            try {
                final String token = auth.split(" ")[1].trim();

                Long memberId = jwtTokenizer.getUserIdFromToken(token);

                ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                        .header("memberId", String.valueOf(memberId))
                        .build();
                log.error("성공" + memberId);

                // 변경된 요청으로 필터 체인 진행
                return chain.filter(exchange.mutate().request(modifiedRequest).build());

            }catch (Exception e){
                log.error("Error occurs token");

                exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                return exchange.getResponse().setComplete();
            }


//            if (config.isPre()) {
//                System.out.println("pre local filter 1");
//            }
//
//
//            return chain.filter(exchange)
//                    .then(Mono.fromRunnable(() -> {
//
//                        if (config.isPost()) {
//
//                            System.out.println("post local filter 1");
//                        }
//                    }));
        };
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Config {
        private boolean pre;
        private boolean post;
    }
}