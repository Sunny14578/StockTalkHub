package com.stocktalkhub.stocktalkhub.service;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ResilienceService {
    @Retry(name = "customRetry", fallbackMethod = "retryFallback")
    public String retry() throws InterruptedException {
        log.info("[TestService] retry");
        throw new RuntimeException();
    }

    private String retryFallback(Exception e) {
        log.info("[TestService] retryFallback");
        return "retryFallback";
    }
}
