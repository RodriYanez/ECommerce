package com.kairos.ecommerce.infrastructure.persistence.mapper;

import com.kairos.ecommerce.domain.models.Price;
import com.kairos.ecommerce.infrastructure.persistence.entities.PriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PriceEntityMapper {
    PriceEntityMapper INSTANCE = Mappers.getMapper(PriceEntityMapper.class);

    Price priceEntityToPrice(PriceEntity priceEntity);

    PriceEntity priceToPriceEntity(Price price);
}
