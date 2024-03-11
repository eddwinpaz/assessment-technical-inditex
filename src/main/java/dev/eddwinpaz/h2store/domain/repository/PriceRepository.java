package dev.eddwinpaz.h2store.domain.repository;

import dev.eddwinpaz.h2store.domain.model.Price;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface PriceRepository extends CrudRepository<Price, Integer> {
    @Query("""
            SELECT *
            FROM Price
            WHERE price.brand_id = :brandId
            AND price.product_id = :productId
            AND :dateTime 
            BETWEEN price.start_date 
            AND price.end_date
            ORDER BY price.priority DESC
            LIMIT 1
            """)
    Iterable<Price> filterByDate(
            @Param("dateTime") LocalDateTime dateTime,
            @Param("brandId") Integer brandId,
            @Param("productId") Integer productId);

}
