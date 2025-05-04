package com.inditex.priceApi.infraestructure.mapper;

import com.inditex.priceApi.domain.model.Price;
import com.inditex.priceApi.infraestructure.entity.PriceEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = BrandMapper.class)
public interface PriceMapper {

    Price entityToPrice(PriceEntity priceEntity);
    PriceEntity priceToEntity(Price price);
}