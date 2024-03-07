package com.stocktalkhub.stocktalkhub.config;

import com.stocktalkhub.stocktalkhub.config.Stock.*;
import com.stocktalkhub.stocktalkhub.config.StockPrice.StockPriceProcessor;
import com.stocktalkhub.stocktalkhub.config.StockPrice.StockPriceReader;
import com.stocktalkhub.stocktalkhub.config.StockPrice.StockPriceWriter;
import com.stocktalkhub.stocktalkhub.domain.Stock;
import com.stocktalkhub.stocktalkhub.domain.StockPrice;
import com.stocktalkhub.stocktalkhub.dto.StockInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class BatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final StepTimeListener stepTimeListener;
    private final StockKospiReader stockKospiReader;
    private final StockKosdaqReader stockKosdaqReader;
    private final StockProcessor stockProcessor;
    private final StockWriter stockWriter;
    private final StockPriceReader stockPriceReader;
    private final StockPriceProcessor stockPriceProcessor;
    private final StockPriceWriter stockPriceWriter;

    @Bean
    public Job stockJob(){
        log.info("job 시작");
        return jobBuilderFactory.get("stockJob")
                .start(stepGetStocksKOSPI())
                .next(stepGetStocksKOSDAQ())
                .build();
    }

    @Bean
    public Job stockPriceJob(){
        return jobBuilderFactory.get("stockPriceJob")
                .incrementer(new RunIdIncrementer())
                .start(stepGetStockPrices())
                .build();
    }

    @Bean
    public Step stepGetStocksKOSPI(){
        return stepBuilderFactory.get("step")
                .<JSONArray, List<Stock>>chunk(10) // 청크 사이즈를 10으로 변경
                .reader(stockKospiReader)
                .processor(stockProcessor) // 원하는 작업을 수행하는 ItemProcessor를 사용
                .writer(stockWriter)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .listener(stepTimeListener)
                .build();
    }

    @Bean
    public Step stepGetStocksKOSDAQ(){
        return stepBuilderFactory.get("step")
                .<JSONArray, List<Stock>>chunk(10) // 청크 사이즈를 10으로 변경
                .reader(stockKosdaqReader)
                .processor(stockProcessor)
                .writer(stockWriter)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @Bean
    public Step stepGetStockPrices(){
        return stepBuilderFactory.get("step")
                .<StockInfoDTO, List<StockPrice>>chunk(100)
                .reader(stockPriceReader)
                .processor(stockPriceProcessor)
                .writer(stockPriceWriter)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

//    @Bean
//    public Step stepEvaluateSupplementaryMetrics(){
//        return stepBuilderFactory.get("step")
//                .<StockInfoDTO, List<StockPrice>>chunk(100)
//                .reader(stockPriceReader)
//                .processor(stockPriceProcessor)
//                .writer(stockPriceWriter)
//                .taskExecutor(new SimpleAsyncTaskExecutor())
//                .build();
//    }
}
