package com.inditex.priceApi.application.service;

import com.inditex.priceApi.domain.model.Price;

import java.time.LocalDateTime;

public interface PriceService {
    Price getApplicablePrice(Integer productId, Integer brandId, LocalDateTime applicationDate);
}
