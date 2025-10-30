package com.kairos.ecommerce.infrastructure.persistence.mapper;

import java.time.LocalDateTime;

import com.kairos.ecommerce.domain.models.Price;
import com.kairos.ecommerce.infrastructure.persistence.entities.PriceEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PriceEntityMapperTest {

    private final PriceEntityMapper mapper = PriceEntityMapper.INSTANCE;

    @Test
    void shouldMapEntityToDomainCorrectly() {
        // given
        final var start = LocalDateTime.of(2020, 6, 14, 0, 0);
        final var end = LocalDateTime.of(2020, 6, 14, 23, 59);

        final var entity = PriceEntity.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(2L)
                .priority(1L)
                .price(35.5f)
                .currency("EUR")
                .startDate(start)
                .endDate(end)
                .build();
        final var expectedResult = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(2L)
                .priority(1L)
                .price(35.5f)
                .currency("EUR")
                .startDate(start)
                .endDate(end)
                .build();

        // when
        final var domain = mapper.priceEntityToPrice(entity);

        // then
        assertThat(domain).isNotNull();
        assertThat(domain).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    void shouldMapDomainToEntityCorrectly() {
        // given
        final var start = LocalDateTime.of(2020, 6, 15, 10, 0);
        final var end = LocalDateTime.of(2020, 6, 15, 22, 0);

        final var domain = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(3L)
                .priority(2L)
                .price(25.45f)
                .currency("USD")
                .startDate(start)
                .endDate(end)
                .build();
        final var expectedResult = PriceEntity.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(3L)
                .priority(2L)
                .price(25.45f)
                .currency("USD")
                .startDate(start)
                .endDate(end)
                .build();

        // when
        final var entity = mapper.priceToPriceEntity(domain);

        // then
        assertThat(entity).isNotNull();
        assertThat(entity).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    void shouldHandleNullsGracefully() {
        assertThat(mapper.priceEntityToPrice(null)).isNull();
        assertThat(mapper.priceToPriceEntity(null)).isNull();
    }
}
