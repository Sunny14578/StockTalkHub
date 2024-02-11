package org.example.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final CustomFilter customFilter;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/user-module/members/login")
                        .uri("http://localhost:8080"))

                .route(r -> r.path("/user-module/members/join")
                        .uri("http://localhost:8080"))

                .route(r -> r.path("/user-module/members/emailCheck")
                        .uri("http://localhost:8080"))

                .route(r -> r.path("/stock-module/**")
                        .uri("http://localhost:8084"))

                .route(r -> r.path("/user-module/**")
                                .filters(f -> f.filter(customFilter.apply(new CustomFilter.Config(true, true))))
                                .uri("http://localhost:8080"))

                .route(r -> r.path("/activity-module/**")
                .filters(f -> f.filter(customFilter.apply(new CustomFilter.Config(true, true))))
                .uri("http://localhost:8081"))


                .route(r -> r.path("/newsfeed-module/**")
                .filters(f -> f.filter(customFilter.apply(new CustomFilter.Config(true, true))))
                .uri("http://localhost:8082"))
                .build();
    }
}
