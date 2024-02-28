package com.stocktalkhub.stocktalkhub.config.Stock;

import com.stocktalkhub.stocktalkhub.service.StocksService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockKospiReader implements ItemReader<JSONArray> {
    private final StocksService stocksService;
    private int currentPage = 1;

    @Override
    public JSONArray read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        JSONArray response = stocksService.getStockKospiAPI(currentPage);

        currentPage++;
        if (response == null || response.isEmpty()){
            return null;
        }

        return response;
    }
}
