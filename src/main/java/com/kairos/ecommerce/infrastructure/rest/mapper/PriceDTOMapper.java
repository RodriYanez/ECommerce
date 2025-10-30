package com.kairos.ecommerce.infrastructure.rest.mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.kairos.ecommerce.domain.models.Price;
import com.kairos.ecommerce.infrastructure.rest.dto.PriceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PriceDTOMapper {

    PriceDTOMapper INSTANCE = Mappers.getMapper(PriceDTOMapper.class);

    @Mapping(source = "priceList", target = "tariffId")
    @Mapping(source = "startDate", target = "startDate", qualifiedByName = "localToOffset")
    @Mapping(source = "endDate", target = "endDate", qualifiedByName = "localToOffset")
    PriceDTO toDTO(Price price);

    @Mapping(source = "tariffId", target = "priceList")
    @Mapping(source = "startDate", target = "startDate", qualifiedByName = "offsetToLocal")
    @Mapping(source = "endDate", target = "endDate", qualifiedByName = "offsetToLocal")
    Price toDomain(PriceDTO dto);

    @Named("localToOffset")
    default OffsetDateTime localToOffset(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atOffset(ZoneOffset.UTC);
    }

    @Named("offsetToLocal")
    default LocalDateTime offsetToLocal(OffsetDateTime offsetDateTime) {
        return offsetDateTime == null ? null : offsetDateTime.toLocalDateTime();
    }
}
