package com.stocktalkhub.stocktalkhub.config.Stock;

import com.stocktalkhub.stocktalkhub.domain.Stock;
import com.stocktalkhub.stocktalkhub.repository.StocksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class StockWriter implements ItemWriter<List<Stock>> {

    private final StocksRepository stocksRepository;
    // 예시로 출력만 하도록 작성하였습니다. 실제로는 이를 데이터베이스에 저장하거나 파일에 기록하는 등의 작업을 수행해야 합니다.
    @Override
    public void write(List<? extends List<Stock>> items) throws Exception {

        List<Stock> flattenedList = items.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        stocksRepository.saveAll(flattenedList);

//        for (List<Stock> batch : items) {
//            stocksRepository.saveAll(batch);
//        }
    }
}

