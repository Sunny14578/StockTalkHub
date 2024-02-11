package com.stocktalkhub.stocktalkhub.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class SchedulerService {

    private final StocksService stocksService;
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // 5초마다 실행
    public void myScheduledMethod() {
        stocksService.stocksInsert();
        // 스케줄링될 작업 수행
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        stocksService.getStocksPriceApi();
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }
}
