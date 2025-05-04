package com.inditex.priceApi.application.service;

import com.inditex.priceApi.domain.model.Price;
import com.inditex.priceApi.domain.port.PricePort;

import java.time.LocalDateTime;

public class DomainPriceService implements PriceService {
    private final PricePort pricePort;

    public DomainPriceService(PricePort pricePort) {
        this.pricePort = pricePort;
    }

    @Override
    public Price getApplicablePrice(Integer productId, Integer brandId, LocalDateTime applicationDate) {
        return pricePort.getApplicablePrice(productId, brandId, applicationDate);
    }

}
