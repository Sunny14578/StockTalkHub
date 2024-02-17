package com.stocktalkhub.stocktalkhub.config;

import com.stocktalkhub.stocktalkhub.service.StocksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class BatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final StocksService stocksService;

    @Bean
    public Job stockJob(){
        log.info("job 시작");
        return jobBuilderFactory.get("stockJob")
                .start(stepGetStocks())
                .build();
    }

    @Bean
    public Step stepGetStocks(){
        return stepBuilderFactory.get("step")
                .tasklet((contribution, chunkContext) -> {
                    log.info("스텝1");
//                    stocksService.test();
                    stocksService.getStocksAPI(); // 첫 번째 메서드 실행
                    log.info("스텝2");
                    stocksService.getStocksPriceAPI();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

//    @Bean
//    public Step stepInsertStocksPrice(){
//        return stepBuilderFactory.get("step")
//                .tasklet((contribution, chunkContext) -> {
//                    log.info("스텝2");
//                    stocksService.stockKosdaqInsertAll(); // 첫 번째 메서드 실행
//
//                    return RepeatStatus.FINISHED;
//                })
//                .build();
//    }

}
