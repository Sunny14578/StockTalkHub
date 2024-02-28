package com.stocktalkhub.stocktalkhub.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stockId")
    private Stock stock;

    @Column(name = "date")
    private LocalDate date;
    @Column(name = "open")
    private double open;
    @Column(name = "high")
    private double high;

    @Column(name = "low")
    private double low;

    @Column(name = "close")
    private double close;

    @Column(name = "volume")
    private long volume;

    @Column(name = "fluctuation")
    private double fluctuation;

    @Column(name = "moving_average_12")
    private double movingAverage12;

    @Column(name = "moving_average_20")
    private double movingAverage20;

    @Column(name = "moving_average_26")
    private double movingAverage26;

    @Column(name = "bollinger_upper_band")
    private double bollingerUpperBand;

    @Column(name = "bollinger_lower_band")
    private double bollingerLowerBand;

    @Column(name = "macd")
    private double macd;

    @Builder
    private StockPrice(Stock stock, LocalDate date,
                       double open, double high,
                       double low, double close,
                       double movingAverage12, double movingAverage20, double movingAverage26,
                       long volume, double fluctuation,
                       double bollingerLowerBand, double bollingerUpperBand, double macd) {
        this.stock = stock;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.movingAverage12 = movingAverage12;
        this.movingAverage20 = movingAverage20;
        this.movingAverage26 = movingAverage26;
        this.fluctuation = fluctuation;
        this.bollingerUpperBand = bollingerUpperBand;
        this.bollingerLowerBand = bollingerLowerBand;
        this.macd = macd;
    }
}
