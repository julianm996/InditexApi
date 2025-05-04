package com.inditex.priceApi.domain.port;

import com.inditex.priceApi.domain.model.Price;

import java.time.LocalDateTime;

public interface PricePort {
    Price getApplicablePrice(Integer productId, Integer brandId, LocalDateTime applicationDate);
}
