package com.stocktalkhub.stocktalkhub.dto;

import com.stocktalkhub.stocktalkhub.domain.StockType;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
public class StockDTO {

    private String symbol;
    private String name;
    private StockType marketType;

    @Builder
    private StockDTO(String symbol, String name, StockType marketType) {
        this.symbol = symbol;
        this.name = name;
        this.marketType = marketType;
    }
}
