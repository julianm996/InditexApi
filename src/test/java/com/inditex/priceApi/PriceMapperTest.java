package com.inditex.priceApi;

import com.inditex.priceApi.domain.model.*;
import com.inditex.priceApi.infraestructure.entity.*;
import com.inditex.priceApi.infraestructure.mapper.PriceMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PriceMapperTest {

    @Autowired
    private PriceMapper mapper;

    @Test
    public void should_map_entity_to_domain_correctly() {
        BrandEntity brandEntity = new BrandEntity(1L, "ZARA");
        PriceEntity entity = new PriceEntity(
                1L, brandEntity,
                LocalDateTime.parse("2020-06-14T00:00:00"),
                LocalDateTime.parse("2020-06-14T23:59:59"),
                1, 35455, 0,
                BigDecimal.valueOf(35.50),
                Currency.EUR
        );

        Price domain = mapper.entityToPrice(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getBrand().getId());
        assertEquals("ZARA", domain.getBrand().getName());
    }

    @Test
    public void should_map_domain_to_entity_correctly() {
        Brand brand = new Brand(1L, "ZARA");
        Price price = new Price(
                1L, brand,
                LocalDateTime.parse("2020-06-14T00:00:00"),
                LocalDateTime.parse("2020-06-14T23:59:59"),
                1, 35455, 0,
                BigDecimal.valueOf(35.50),
                Currency.EUR
        );

        PriceEntity entity = mapper.priceToEntity(price);

        assertNotNull(entity);
        assertEquals(1L, entity.getBrand().getId());
        assertEquals("ZARA", entity.getBrand().getName());
        assertEquals(price.getPrice(), entity.getPrice());
    }
}
