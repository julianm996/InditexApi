package com.inditex.priceApi.infraestructure.mapper;

import com.inditex.priceApi.domain.model.Brand;
import com.inditex.priceApi.infraestructure.entity.BrandEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand entityToBrand(BrandEntity brandEntity);
    BrandEntity brandToEntity(Brand brand);
}