package com.kairos.ecommerce.infrastructure.persistence.daos;

import java.time.LocalDateTime;

import com.kairos.ecommerce.infrastructure.persistence.entities.PriceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PricesJPARepositoryITest {

    @Autowired
    private PricesJPARepository repository;

    private static final Long BRAND_ID = 1L;
    private static final Long PRODUCT_ID = 35455L;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        repository.save(PriceEntity.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceList(1L)
                .priority(0L)
                .price(35.50f)
                .currency("EUR")
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .build());

        repository.save(PriceEntity.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceList(2L)
                .priority(1L)
                .price(25.45f)
                .currency("EUR")
                .startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .build());

        repository.save(PriceEntity.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceList(3L)
                .priority(1L)
                .price(30.50f)
                .currency("EUR")
                .startDate(LocalDateTime.of(2020, 6, 15, 0, 0))
                .endDate(LocalDateTime.of(2020, 6, 15, 11, 0))
                .build());
    }

    @Test
    void shouldReturnEmptyListWhenNoMatchingDate() {
        // given
        final var outsideDate = LocalDateTime.of(2019, 1, 1, 0, 0);

        // when
        final var results = repository.findPriceByBrandIdAndProductIdAndDateBetweenStartDateAndEndDate(
                BRAND_ID, PRODUCT_ID, outsideDate);

        // then
        assertThat(results).isEmpty();
    }

    @Test
    void shouldFilterByBrandAndProduct() {
        // given
        final var date = LocalDateTime.of(2020, 6, 14, 10, 0);

        // when
        final var wrongBrand = repository.findPriceByBrandIdAndProductIdAndDateBetweenStartDateAndEndDate(99L, PRODUCT_ID, date);
        final var wrongProduct = repository.findPriceByBrandIdAndProductIdAndDateBetweenStartDateAndEndDate(BRAND_ID, 99999L, date);

        // then
        assertThat(wrongBrand).isEmpty();
        assertThat(wrongProduct).isEmpty();
    }
}

