package com.stocktalkhub.stocktalkhub.config;

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
public class StockListReader implements ItemReader<JSONArray> {
    private final StocksService stocksService;
    private int currentPage = 1;

    @Override
    public JSONArray read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        JSONArray response = stocksService.getStockAPI(currentPage);
        System.out.println(response + "확인해보자");
        currentPage++;
        if (response == null || response.isEmpty()){
            return null;
        }

        return response;
//        JSONArray response = null;
//        System.out.println("리드시작");
//        for (int i = 1; i <= 22; i++){
//            response =  stocksService.getStockAPI(i);
//
//            if (response != null){
//                return response;
//            }else{
//                return null;
//            }
//        }

    }
}
