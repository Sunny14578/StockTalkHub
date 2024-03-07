package com.stocktalkhub.stocktalkhub.repository;

import com.stocktalkhub.stocktalkhub.domain.StockPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Transactional
@Repository
@RequiredArgsConstructor
public class StocksPriceRepository {
    private final JdbcTemplate jdbcTemplate;
    private final EntityManager em;

    public void saveAll(List<StockPrice> stockPrices) {
        String sql = "INSERT INTO stock_price (stock_id, date, open, high, low, close, volume, fluctuation, moving_average_12, " +
                "moving_average_20, moving_average_26, bollinger_upper_band, bollinger_lower_band, macd) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, stockPrices, stockPrices.size(), (ps, stockPrice) -> {
            ps.setLong(1, stockPrice.getStock().getId());
            ps.setDate(2, Date.valueOf(stockPrice.getDate()));
            ps.setDouble(3, stockPrice.getOpen());
            ps.setDouble(4, stockPrice.getHigh());
            ps.setDouble(5, stockPrice.getLow());
            ps.setDouble(6, stockPrice.getClose());
            ps.setLong(7, stockPrice.getVolume());
            ps.setDouble(8, stockPrice.getFluctuation());
            ps.setDouble(9, stockPrice.getMovingAverage12());
            ps.setDouble(10, stockPrice.getMovingAverage20());
            ps.setDouble(11, stockPrice.getMovingAverage26());
            ps.setDouble(12, stockPrice.getBollingerUpperBand());
            ps.setDouble(13, stockPrice.getBollingerLowerBand());
            ps.setDouble(14, stockPrice.getMacd());
        });
    }


    public List<StockPrice> findAll(Long stockId, int days) {

        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusDays(days);

        String jpql = "SELECT sp FROM StockPrice sp WHERE sp.stock.id = :stockId AND sp.date >= :startDate AND sp.date <= :endDate";

        TypedQuery<StockPrice> query = em.createQuery(jpql, StockPrice.class);
        query.setParameter("stockId", stockId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", currentDate);

        return query.getResultList();

    }

    public StockPrice findOne(Long stockId) {

        String jpql = "SELECT sp FROM StockPrice sp WHERE sp.stock.id = :stockId";

        TypedQuery<StockPrice> query = em.createQuery(jpql, StockPrice.class);
        query.setParameter("stockId", stockId);

        List<StockPrice> resultList = query.getResultList();
        int size = resultList.size();

        return resultList.get(size - 1); // 리스트의 마지막 요소 가져오기
    }
}
