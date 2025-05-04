package com.inditex.priceApi.infraestructure.adapter;

import com.inditex.priceApi.domain.model.Price;
import com.inditex.priceApi.infraestructure.exceptions.PriceException;
import com.inditex.priceApi.infraestructure.mapper.PriceMapper;
import com.inditex.priceApi.infraestructure.repositories.PriceRepository;
import org.springframework.stereotype.Repository;
import com.inditex.priceApi.domain.port.PricePort;

import java.time.LocalDateTime;

@Repository
public class PricePortAdapter implements PricePort {

    private final PriceRepository priceRepository;
    private PriceMapper priceMapper;

    public PricePortAdapter(PriceRepository priceRepository,
                            PriceMapper priceMapper) {
        this.priceRepository = priceRepository;
        this.priceMapper = priceMapper;
    }

    @Override
    public Price getApplicablePrice(Integer productId, Integer brandId, LocalDateTime applicationDate) {
        return priceMapper.entityToPrice(this.priceRepository
                        .findTopByProductIdAndBrandIdAndApplicationDate(
                                productId,brandId,applicationDate)
                        .orElseThrow(() -> new PriceException(
                                "No se encontró un precio aplicable para el productId=" + productId + ", brandId=" + brandId + ", date=" + applicationDate)
                        ));
    }

}
