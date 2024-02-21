package com.stocktalkhub.stocktalkhub.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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
    private static final String StockUrlforCookies = "https://finance.naver.com/sise/field_submit.naver?menu=market_sum&returnUrl=http%3A%2F%2Ffinance.naver.com%2Fsise%2Fsise_market_sum.naver&fieldIds=quant&fieldIds=market_sum&fieldIds=open_val&fieldIds=prev_quant&fieldIds=high_val&fieldIds=low_val";
    private static final String NaverStockUrl = "https://finance.naver.com/sise/sise_market_sum.naver";

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

    public JSONArray getStockAPI(int i) {
        RestTemplate restTemplate = new RestTemplate();

        // 종목코드를 바꿔가면서, 가지고올 주가 일수에 따라 URL 정의
        String url = "https://m.stock.naver.com/api/stocks/marketValue/KOSPI?page=" + i + "&pageSize=100";

        // JSON 형태로 데이터 가져오기
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String jsonData = responseEntity.getBody();
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray stocksArray = jsonObject.getJSONArray("stocks");

        return stocksArray;
    }

    public void getStocksAPI(){
        RestTemplate restTemplate = new RestTemplate();

        for (int i = 1; i <= 22; i++){

            // 종목코드를 바꿔가면서, 가지고올 주가 일수에 따라 URL 정의
            String url = "https://m.stock.naver.com/api/stocks/marketValue/KOSPI?page="+i+"&pageSize=100";

            // JSON 형태로 데이터 가져오기
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            String jsonData = responseEntity.getBody();
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray stocksArray = jsonObject.getJSONArray("stocks");
            stockKospiInsertAll(stocksArray);
        }

    }

    public void stockKospiInsertAll(JSONArray jsonObject){
        List<Stock> stockList = new ArrayList<>();

        for (Object obj : jsonObject) {
            JSONObject stock = (JSONObject) obj;
            String itemCode = stock.getString("itemCode");
            String stockName = stock.getString("stockName");

            Stock stockEntity = Stock.builder()
                    .symbol(itemCode)
                    .name(stockName)
                    .marketType(StockType.KOSPI)
                    .build();

            stockList.add(stockEntity);
        }
        stocksRepository.saveAll(stockList);
    }


    public void stockKosdaqInsertAll(JSONArray jsonObject) {
        List<Stock> stockList = new ArrayList<>();

        for (Object obj : jsonObject) {
            JSONObject stock = (JSONObject) obj;
            String itemCode = stock.getString("itemCode");
            String stockName = stock.getString("stockName");

            Stock stockEntity = Stock.builder()
                    .symbol(itemCode)
                    .name(stockName)
                    .marketType(StockType.KOSDAQ)
                    .build();

            stockList.add(stockEntity);
        }
        stocksRepository.saveAll(stockList);
    }

    public static String convertXmlToJson(String xmlData) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        JsonNode jsonNode = xmlMapper.readTree(xmlData.getBytes());
        ObjectMapper jsonMapper = new ObjectMapper();
        return jsonMapper.writeValueAsString(jsonNode);
    }

    public void getStocksPriceAPI() throws Exception {
        List<Stock> stocks = stocksRepository.findAll();

        RestTemplate restTemplate = new RestTemplate();
//        List<StockPrice> stockPriceList = new ArrayList<>();

        for (Stock stock : stocks) {

            String count = "30";

            // 종목코드를 바꿔가면서, 가지고올 주가 일수에 따라 URL 정의
            String url = "https://fchart.stock.naver.com/sise.nhn"
                    + "?symbol=" + stock.getSymbol()
                    + "&timeframe=day"
                    + "&count=" + count
                    + "&requestType=0";

            // JSON 형태로 데이터 가져오기
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            String xmlData = responseEntity.getBody();
            String jsonData = convertXmlToJson(xmlData);

            extractValuesFromJson(jsonData, stock);
//            stockPriceList.add(stockPrice);
        }
    }

    public void extractValuesFromJson(String jsonData, Stock stock) throws Exception {

        List<StockPrice> stockPriceList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);

        JsonNode chartdataNode = rootNode.get("chartdata");

        JsonNode itemNode = chartdataNode.get("item");

        for (JsonNode item : itemNode){
            String itemData = item.get("data").asText();

//            StockPrice stockPrice = stocksPriceInsertDay(itemData, stock, stockPriceList);

            String[] data = itemData.split("\\|");

            LocalDate date = LocalDate.parse(data[0], DateTimeFormatter.ofPattern("yyyyMMdd"));
            double open = Double.parseDouble(data[1]);
            double high = Double.parseDouble(data[2]);
            double low = Double.parseDouble(data[3]);
            double close = Double.parseDouble(data[4]);
            long volume = Long.parseLong(data[5]);

            double previousClose = stockPriceList.isEmpty() ? close : stockPriceList.get(stockPriceList.size() - 1).getClose();
            double fluctuation = ((close - previousClose) / previousClose) * 100;

            StockPrice stocksPrice = StockPrice.builder()
                    .stock(stock)
                    .date(date)
                    .open(open)
                    .high(high)
                    .low(low)
                    .close(close)
                    .volume(volume)
                    .fluctuation(fluctuation)
                    .build();

            stockPriceList.add(stocksPrice);
        }

        stocksPriceRepository.saveAll(stockPriceList);
    }

//    public StockPrice stocksPriceInsertDay(String item, Stock stock, List<StockPrice> stockPriceList){
//
//
//    }

//        for (int i = 0; i < itemArray.length(); i++) {
//            JSONObject item = itemArray.getJSONObject(i);
//            String[] data = item.getString("data").split("\\|");
//
//            LocalDate date = LocalDate.parse(data[0], DateTimeFormatter.ofPattern("yyyyMMdd"));
//            double open = Double.parseDouble(data[1]);
//            double high = Double.parseDouble(data[2]);
//            double low = Double.parseDouble(data[3]);
//            double close = Double.parseDouble(data[4]);
//            long volume = Long.parseLong(data[5]);
//
//            double previousClose = stockPrices.isEmpty() ? close : stockPrices.get(stockPrices.size() - 1).getClose();
//            double fluctuation = ((close - previousClose) / previousClose) * 100;
//
//            StockPrice stocksPrice = StockPrice.builder()
//                    .stock(stocks)
//                    .date(date)
//                    .open(open)
//                    .high(high)
//                    .low(low)
//                    .close(close)
//                    .volume(volume)
//                    .fluctuation(fluctuation)
//                    .build();
//
//            stockPrices.add(stocksPrice);
//        }

//        stocksPriceRepository.saveAll(stockPrices);
//    }

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