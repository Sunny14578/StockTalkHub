package com.stocktalkhub.stocktalkhub.dto;

import com.stocktalkhub.stocktalkhub.domain.Stock;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor
public class StockPriceDTO {
    private Stock stock;
    private LocalDate date;
    private double open;
    private double high;
    private double low;
    private double close;
    private long volume;
    private double fluctuation;

    @Builder
    private StockPriceDTO(Stock stock, LocalDate date,
                          double open, double high,
                          double low, double close,
                          long volume, double fluctuation) {
        this.stock = stock;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.fluctuation = fluctuation;
    }
}
