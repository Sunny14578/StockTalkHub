package com.stocktalkhub.stocktalkhub.service;

import com.stocktalkhub.stocktalkhub.domain.Stock;
import com.stocktalkhub.stocktalkhub.domain.StockPrice;
import com.stocktalkhub.stocktalkhub.domain.StockType;
import com.stocktalkhub.stocktalkhub.dto.StockPriceDTO;
import com.stocktalkhub.stocktalkhub.repository.StocksPriceRepository;
import com.stocktalkhub.stocktalkhub.repository.StocksRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class StocksService {
    private final StocksRepository stocksRepository;
    private final StocksPriceRepository stocksPriceRepository;

    public List<StockPriceDTO> getStocksAll(int pageNumber, int pageSize, String orderBy, String direction) {
        List<Stock> stocks = stocksRepository.findAllPaginated(pageNumber, pageSize, orderBy, direction);
//        boolean volume, boolean fluctuation

        List<StockPriceDTO> stocksPriceDTOs = new ArrayList<>();

        for (Stock stock : stocks) {

            StockPrice stockPrice = stocksPriceRepository.findOne(stock.getId());;

            StockPriceDTO stockPriceDTO = StockPriceDTO.builder()
                    .stock(stock)
                    .date(stockPrice.getDate())
                    .open(stockPrice.getOpen())
                    .high(stockPrice.getHigh())
                    .low(stockPrice.getLow())
                    .close(stockPrice.getClose())
                    .volume(stockPrice.getVolume())
                    .fluctuation(stockPrice.getFluctuation())
                    .build();

            stocksPriceDTOs.add(stockPriceDTO);
        }

        if ("volume".equalsIgnoreCase(orderBy)) {
            stocksPriceDTOs.sort(Comparator.comparingDouble(StockPriceDTO::getVolume));
            if ("DESC".equalsIgnoreCase(direction)) {
                Collections.reverse(stocksPriceDTOs);
            }
        }
// 등락률(fluctuation) 기준으로 정렬
        else if ("fluctuation".equalsIgnoreCase(orderBy)) {
            stocksPriceDTOs.sort(Comparator.comparingDouble(StockPriceDTO::getFluctuation));
            if ("DESC".equalsIgnoreCase(direction)) {
                Collections.reverse(stocksPriceDTOs);
            }
        }

        return stocksPriceDTOs;
    }



    public void stocksInsert() {

        List<Stock> stockList = new ArrayList<>();

        Stock stock1 = Stock.builder()
                .symbol("005930")
                .name("삼성전자")
                .marketType(StockType.KOSPI)
                .build();

        Stock stock2 = Stock.builder()
                .symbol("035720")
                .name("카카오")
                .marketType(StockType.KOSPI)
                .build();

        Stock stock3 = Stock.builder()
                .symbol("000660")
                .name("SK하이닉스")
                .marketType(StockType.KOSPI)
                .build();

        Stock stock4 = Stock.builder()
                .symbol("030200")
                .name("KT")
                .marketType(StockType.KOSPI)
                .build();

        Stock stock5 = Stock.builder()
                .symbol("035420")
                .name("네이버")
                .marketType(StockType.KOSPI)
                .build();

        stockList.add(stock1);
        stockList.add(stock2);
        stockList.add(stock3);
        stockList.add(stock4);
        stockList.add(stock5);

        for (Stock stock : stockList) {
            stocksRepository.save(stock);
        }
    }

    public void getStocksPriceApi() {
        String[] stockCodes = {"005930", "035720", "000660", "030200", "035420"};
        RestTemplate restTemplate = new RestTemplate();

        for (String code : stockCodes) {

            String count = "1500";

            // 종목코드를 바꿔가면서, 가지고올 주가 일수에 따라 URL 정의
            String url = "https://fchart.stock.naver.com/sise.nhn"
                    + "?symbol=" + code
                    + "&timeframe=day"
                    + "&count=" + count
                    + "&requestType=0";


            // JSON 형태로 데이터 가져오기
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            String jsonData = responseEntity.getBody();

            // XML 형태로 온 데이터를 JSON으로 변환
            JSONObject jsonObject = XML.toJSONObject(jsonData);
            stocksPriceInsert(jsonObject);
        }
    }

    public void stocksPriceInsert(JSONObject jsonObject) {
        JSONObject chartData = jsonObject.getJSONObject("protocol").getJSONObject("chartdata");
        String symbol = chartData.getString("symbol");
        JSONArray itemArray = chartData.getJSONArray("item");


        Stock stocks = stocksRepository.findOne(symbol).orElseThrow(() ->
                new IllegalArgumentException("해당 종목이 존재하지 않습니다."));

        List<StockPrice> stockPrices = new ArrayList<>();
        for (int i = 0; i < itemArray.length(); i++) {
            JSONObject item = itemArray.getJSONObject(i);
            String[] data = item.getString("data").split("\\|");

            LocalDate date = LocalDate.parse(data[0], DateTimeFormatter.ofPattern("yyyyMMdd"));
            double open = Double.parseDouble(data[1]);
            double high = Double.parseDouble(data[2]);
            double low = Double.parseDouble(data[3]);
            double close = Double.parseDouble(data[4]);
            long volume = Long.parseLong(data[5]);

            double previousClose = stockPrices.isEmpty() ? close : stockPrices.get(stockPrices.size() - 1).getClose();
            double fluctuation = ((close - previousClose) / previousClose) * 100;

            StockPrice stocksPrice = StockPrice.builder()
                    .stock(stocks)
                    .date(date)
                    .open(open)
                    .high(high)
                    .low(low)
                    .close(close)
                    .volume(volume)
                    .fluctuation(fluctuation)
                    .build();

            stockPrices.add(stocksPrice);
        }

        stocksPriceRepository.saveAll(stockPrices);

    }

    public List<StockPriceDTO> getStocksPrice(String symbol, int days) {

        Stock stocks = stocksRepository.findOne(symbol).orElseThrow(() ->
                new IllegalArgumentException("해당 종목이 존재하지 않습니다."));

        List<StockPrice> stocksPriceList = stocksPriceRepository.findAll(stocks.getId(), days);

        List<StockPriceDTO> stocksPriceDTO = new ArrayList<>();
        for (StockPrice stockPrice : stocksPriceList) {

            StockPriceDTO stockPriceDTO = StockPriceDTO.builder()
                    .stock(stocks)
                    .date(stockPrice.getDate())
                    .open(stockPrice.getOpen())
                    .high(stockPrice.getHigh())
                    .low(stockPrice.getLow())
                    .close(stockPrice.getClose())
                    .volume(stockPrice.getVolume())
                    .build();

            stocksPriceDTO.add(stockPriceDTO);
        }

        return stocksPriceDTO;
    }
}
