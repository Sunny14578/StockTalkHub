package com.stocktalkhub.stocktalkhub.config;

import com.stocktalkhub.stocktalkhub.domain.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
    private final StockListReader stockListReader;
    private final StockProcessor stockProcessor;
    private final StockWriter stockWriter;

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
                .<JSONArray, List<Stock>>chunk(100) // 청크 사이즈를 10으로 변경
                .reader(stockListReader)
                .processor(stockProcessor) // 원하는 작업을 수행하는 ItemProcessor를 사용
                .writer(stockWriter)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .listener(stepTimeListener)
                .build();
    }
}
