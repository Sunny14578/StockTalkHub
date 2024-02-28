package com.stocktalkhub.stocktalkhub.config.StockPrice;

import com.stocktalkhub.stocktalkhub.domain.StockPrice;
import com.stocktalkhub.stocktalkhub.dto.StockInfoDTO;
import com.stocktalkhub.stocktalkhub.service.StocksService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StockPriceProcessor implements ItemProcessor<StockInfoDTO, List<StockPrice>>{

    private final StocksService stocksService;

    @Override
    public List<StockPrice> process(StockInfoDTO stockInfo) throws Exception {

        return stocksService.extractValuesFromJson(stockInfo.getResponse(), stockInfo.getStock());
    }
}
