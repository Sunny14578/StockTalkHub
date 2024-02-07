package com.stocktalkhub.stocktalkhub;

import com.stocktalkhub.stocktalkhub.service.ResilienceService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
public class ResilienceTest {

    @Autowired
    ResilienceService resilienceService;

    @Test
    void retry_test() throws InterruptedException {
        resilienceService.retry();
    }
}
