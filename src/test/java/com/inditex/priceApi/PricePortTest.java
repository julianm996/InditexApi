package com.inditex.priceApi;

import com.inditex.priceApi.infraestructure.entity.PriceEntity;
import com.inditex.priceApi.infraestructure.repositories.PriceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class PricePortTest {

    @Autowired
    private PriceRepository priceRepository;

    @Test
    public void testFindApplicablePricesByProductAndBrandAndDate() {
        LocalDateTime date = LocalDateTime.parse("2020-06-14T16:00:00");

        Optional<PriceEntity> priceOptional =
                priceRepository.findTopByProductIdAndBrandIdAndApplicationDate(35455, 1, date);

        assertTrue(priceOptional.isPresent(), "Expected applicable price to be found");

        PriceEntity priceEntity = priceOptional.get();

        assertEquals(2, priceEntity.getPriceList().intValue());
        assertEquals(1, priceEntity.getPriority().intValue());
    }

    @Test
    public void should_return_empty_when_no_price_matches() {
        Optional<PriceEntity> price = priceRepository.findTopByProductIdAndBrandIdAndApplicationDate(
                99999, 1, LocalDateTime.parse("2020-06-14T16:00:00"));
        assertTrue(price.isEmpty());
    }
}