package com.kairos.ecommerce.infrastructure.persistence.mapper;

import com.kairos.ecommerce.domain.models.Price;
import com.kairos.ecommerce.infrastructure.persistence.entities.PriceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceEntityMapper {
    Price priceEntityToPrice(PriceEntity priceEntity);

    PriceEntity priceToPriceEntity(Price price);
}
