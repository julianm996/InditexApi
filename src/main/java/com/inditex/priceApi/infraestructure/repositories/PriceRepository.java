package com.inditex.priceApi.infraestructure.repositories;

import com.inditex.priceApi.infraestructure.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<PriceEntity, Long> {

    @Query(value = """
    SELECT p.id, p.brand_id, p.start_date, p.end_date, p.price_list, 
           p.product_id, p.priority, p.price, p.currency
    FROM prices p 
    WHERE p.product_id = :productId 
      AND p.brand_id = :brandId
      AND :applicationDate BETWEEN p.start_date AND p.end_date
    ORDER BY p.priority DESC
    LIMIT 1
""", nativeQuery = true)
    Optional<PriceEntity> findTopByProductIdAndBrandIdAndApplicationDate(
            Integer productId,
            Integer brandId,
            LocalDateTime applicationDate
    );
}
