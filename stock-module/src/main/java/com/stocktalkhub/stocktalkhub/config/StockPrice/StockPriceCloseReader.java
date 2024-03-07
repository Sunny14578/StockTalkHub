package com.stocktalkhub.stocktalkhub.config.StockPrice;

import com.stocktalkhub.stocktalkhub.domain.StockPrice;
import com.stocktalkhub.stocktalkhub.service.StocksService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
@StepScope
@RequiredArgsConstructor
public class StockPriceCloseReader implements ItemReader<StockPrice> {
    private final StocksService stocksService;

    @Override
    public StockPrice read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {


        return null;
    }
}
