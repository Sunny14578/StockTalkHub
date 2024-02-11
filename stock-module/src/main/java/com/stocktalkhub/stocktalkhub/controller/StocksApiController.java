package com.stocktalkhub.stocktalkhub.controller;

import com.stocktalkhub.stocktalkhub.dto.StockPriceDTO;
import com.stocktalkhub.stocktalkhub.service.SchedulerService;
import com.stocktalkhub.stocktalkhub.service.StocksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("stock-module/")
public class StocksApiController {

    private final StocksService stocksService;
    private final SchedulerService schedulerService;

    @GetMapping("stocks")
    public ResponseEntity getStocksAll(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "symbol") String orderBy,
            @RequestParam(defaultValue = "ASC") String direction) {

            List<StockPriceDTO> stocks = stocksService.getStocksAll(pageNumber, pageSize, orderBy, direction);

        return ResponseEntity.status(HttpStatus.OK).body(stocks);
    }

    @GetMapping("stocks/{symbol}")
    public ResponseEntity getStocksInfo(@PathVariable String symbol,
                                        @RequestParam(defaultValue = "30") int days){

        List<StockPriceDTO> stocksPriceList = stocksService.getStocksPrice(symbol, days);

        return ResponseEntity.status(HttpStatus.OK).body(stocksPriceList);
    }


}
