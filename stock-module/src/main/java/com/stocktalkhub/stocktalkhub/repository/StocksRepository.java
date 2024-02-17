package com.stocktalkhub.stocktalkhub.repository;

import com.stocktalkhub.stocktalkhub.domain.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StocksRepository {
    private final EntityManager em;
    private final JdbcTemplate jdbcTemplate;

    public void save(Stock stock) {
        em.persist(stock);
        System.out.print("성공");
    }

    public Optional<Stock> findOne(String symbol) {
        TypedQuery<Stock> query = em.createQuery(
                "SELECT s FROM Stock s WHERE s.symbol = :symbol", Stock.class);
        query.setParameter("symbol", symbol);
        Stock stocks = query.getSingleResult();

        return Optional.ofNullable(stocks);
    }

    public List<Stock> findAll() {

        List<Stock> resultList = em.createQuery("SELECT s FROM Stock s", Stock.class)
                .getResultList();

        return resultList;
    }

    public List<Stock> findAllPaginated(int pageNumber, int pageSize, String orderBy, String direction) {
        int firstResult = (pageNumber - 1) * pageSize; // 페이지 번호와 페이지 크기를 이용하여 첫 번째 결과의 인덱스를 계산합니다.

        String queryString = "SELECT s FROM Stock s";

        if ("symbol".equalsIgnoreCase(orderBy)) {
            queryString += " ORDER BY s." + orderBy; // 정렬 기준 추가
            queryString += " " + direction.toUpperCase(); // 정렬 방향 추가
        }

        List<Stock> resultList = em.createQuery(queryString, Stock.class)
                .setFirstResult(firstResult) // 첫 번째 결과의 인덱스 설정
                .setMaxResults(pageSize) // 가져올 최대 결과 수 설정
                .getResultList();

        return resultList;
    }


    public void saveAll(List<Stock> stockList) {
        String sql = "INSERT INTO stocks (symbol, name, market_type) VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, stockList, stockList.size(), (ps, stock) -> {
            ps.setString(1, stock.getSymbol());
            ps.setString(2, stock.getName());
            ps.setString(3, stock.getMarketType().toString());
        });
    }
}



