package com.stocktalkhub.stocktalkhub.dto;

import com.stocktalkhub.stocktalkhub.domain.Stock;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StockInfoDTO {
    private Stock stock;
    private String response;

    public StockInfoDTO(Stock stock, String response) {
        this.stock = stock;
        this.response = response;
    }
}
