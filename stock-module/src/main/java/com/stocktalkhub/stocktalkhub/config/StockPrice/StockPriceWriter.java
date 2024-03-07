package com.stocktalkhub.stocktalkhub.config.StockPrice;

import com.stocktalkhub.stocktalkhub.domain.StockPrice;
import com.stocktalkhub.stocktalkhub.repository.StocksPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StockPriceWriter implements ItemWriter<List<StockPrice>> {
    private final StocksPriceRepository stocksPriceRepository;

    @Override
    public void write(List<? extends List<StockPrice>> items) throws Exception {

        List<StockPrice> flattenedList = items.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        stocksPriceRepository.saveAll(flattenedList);
    }
}
