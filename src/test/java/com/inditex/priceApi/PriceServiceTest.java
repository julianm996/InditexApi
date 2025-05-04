package com.inditex.priceApi;


import com.inditex.priceApi.infraestructure.entity.BrandEntity;
import com.inditex.priceApi.infraestructure.entity.PriceEntity;
import com.inditex.priceApi.infraestructure.mapper.PriceMapper;
import com.inditex.priceApi.domain.model.Brand;
import com.inditex.priceApi.domain.model.Currency;
import com.inditex.priceApi.domain.model.Price;
import com.inditex.priceApi.domain.port.PricePort;
import com.inditex.priceApi.application.service.PriceService;
import com.inditex.priceApi.infraestructure.repositories.PriceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Optional.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceServiceTest {

    @Autowired
    private PriceService priceService;

    @MockBean
    private PriceRepository priceRepository;

    @MockBean
    private PriceMapper priceMapper;

    @Test
    public void should_return_price_when_data_is_present () {
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T10:00:00");

        BrandEntity brandEntity = new BrandEntity(1L, "ZARA");
        PriceEntity mockEntity = new PriceEntity(
                1L, brandEntity, applicationDate, applicationDate.plusDays(1),
                1, 35455, 0, BigDecimal.valueOf(35.50), Currency.EUR
        );

        Price mappedPrice = new Price(
                1L, new Brand(1L, "ZARA"), applicationDate, applicationDate.plusDays(1),
                1, 35455, 0, BigDecimal.valueOf(35.50), Currency.EUR
        );

        when(priceRepository.findTopByProductIdAndBrandIdAndApplicationDate(35455, 1, applicationDate))
                .thenReturn(Optional.of(mockEntity));

        when(priceMapper.entityToPrice(mockEntity)).thenReturn(mappedPrice);

        Price result = priceService.getApplicablePrice(35455, 1, applicationDate);

        assertNotNull(result);
        assertEquals(Integer.valueOf(35455), result.getProductId());
        assertEquals(Long.valueOf(1), result.getBrand().getId());
        assertEquals(BigDecimal.valueOf(35.50), result.getPrice());
    }
}