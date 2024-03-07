package com.stocktalkhub.stocktalkhub.config.StockPrice;

import com.stocktalkhub.stocktalkhub.domain.Stock;
import com.stocktalkhub.stocktalkhub.dto.StockInfoDTO;
import com.stocktalkhub.stocktalkhub.repository.StocksRepository;
import com.stocktalkhub.stocktalkhub.service.StocksService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import org.springframework.batch.core.StepExecution;

import java.util.Iterator;
import java.util.List;

@Component
@StepScope
@RequiredArgsConstructor
public class StockPriceReader implements ItemReader<StockInfoDTO> {
    private final StocksService stocksService;
    private final StocksRepository stocksRepository;
    private Iterator<Stock> stockIterator;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        List<Stock> stocks = stocksRepository.findAll();

//        Stock selectedStock = stocks.get(0); // 첫 번째 요소 선택
//        stocks.clear(); // 모든 요소 삭제
//        stocks.add(selectedStock);

        this.stockIterator = stocks.iterator();
    }

    @Override
    public StockInfoDTO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (stockIterator.hasNext()) {
            Stock stock = stockIterator.next();

            String response = stocksService.getStocksPrice(stock);

            return new StockInfoDTO(stock, response);
        } else {
            return null; // 더 이상 읽을 데이터가 없으면 null을 반환하여 읽기를 중단합니다.
        }
    }
}
