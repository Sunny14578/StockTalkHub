package com.stocktalkhub.stocktalkhub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Slf4j
//@EnableScheduling
@EnableAsync
@EnableBatchProcessing
@EnableFeignClients
public class StockModuleApplication {

    public static void main(String[] args) {

        int exit = SpringApplication.exit(SpringApplication.run(StockModuleApplication.class, args));
        log.info("exit = {}", exit);
        System.exit(exit);
    }

}
