package com.stocktalkhub.stocktalkhub.config.Stock;

import com.stocktalkhub.stocktalkhub.domain.Stock;
import com.stocktalkhub.stocktalkhub.domain.StockType;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StockProcessor implements ItemProcessor<JSONArray, List<Stock>> {

    @Override
    public List<Stock> process(JSONArray jsonArray) throws Exception {
        List<Stock> processedStocks = new ArrayList<>();

        for (Object obj : jsonArray){
            JSONObject stockJson = (JSONObject) obj;

            String kospi = stockJson.getJSONObject("stockExchangeType").getString("nameKor");
            String itemCode = stockJson.getString("itemCode");
            String stockName = stockJson.getString("stockName");

            Stock stocks = Stock.builder()
                    .name(stockName)
                    .symbol(itemCode)
                    .marketType(kospi.equals("코스피") ? StockType.KOSPI : StockType.KOSDAQ)
                    .build();

            processedStocks.add(stocks);
        }

        return processedStocks;
    }
}
