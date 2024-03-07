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
    public void myScheduledMethod() throws Exception {

//        stocksService.stocksInsert();
        // 스케줄링될 작업 수행
        System.out.println("하하하");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
//        stocksService.getStocksAPI(); // 첫 번째 메서드 실행
//        stocksService.test();
        stopWatch.stop();
        System.out.println("getStocksAPI 실행 시간: " + stopWatch.getLastTaskTimeMillis() + "ms");

//        stopWatch.start();
//        stocksService.getStocksPriceAPI(); // 두 번째 메서드 실행
//        stopWatch.stop();
//        System.out.println("getStocksPriceAPI 실행 시간: " + stopWatch.getLastTaskTimeMillis() + "ms");
    }
}
