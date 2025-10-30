package com.kairos.ecommerce.infrastructure.rest.mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.kairos.ecommerce.domain.models.Price;
import com.kairos.ecommerce.infrastructure.rest.dto.PriceDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class PriceDTOMapperTest {

    private PriceDTOMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(PriceDTOMapper.class);
    }

    @Test
    void shouldMapPriceToDTO() {
        // given
        final var start = LocalDateTime.of(2020, 6, 14, 0, 0);
        final var end = LocalDateTime.of(2020, 12, 31, 23, 59);
        final var price = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(2L)
                .price(35.50f)
                .priority(1L)
                .currency("EUR")
                .startDate(start)
                .endDate(end)
                .build();

        final var expectedResult = PriceDTO.builder()
                .brandId(1L)
                .productId(35455L)
                .tariffId(2L)
                .price(35.50f)
                .currency("EUR")
                .startDate(start.atOffset(ZoneOffset.UTC))
                .endDate(end.atOffset(ZoneOffset.UTC))
                .build();

        // when
        final var dto = mapper.toDTO(price);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    void shouldMapDTOToPrice() {
        // given
        final var start = OffsetDateTime.of(2020, 6, 14, 0, 0, 0, 0, ZoneOffset.UTC);
        final var end = OffsetDateTime.of(2020, 12, 31, 23, 59, 0, 0, ZoneOffset.UTC);
        final var dto = PriceDTO.builder()
                .brandId(1L)
                .productId(35455L)
                .tariffId(3L)
                .price(25.45f)
                .currency("EUR")
                .startDate(start)
                .endDate(end)
                .build();
        final var expectedResult = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(3L)
                .price(25.45f)
                .currency("EUR")
                .startDate(start.toLocalDateTime())
                .endDate(end.toLocalDateTime())
                .build();

        // when
        final var price = mapper.toDomain(dto);

        // then
        assertThat(price).isNotNull();
        assertThat(price).usingRecursiveComparison().isEqualTo(expectedResult);
    }
}
