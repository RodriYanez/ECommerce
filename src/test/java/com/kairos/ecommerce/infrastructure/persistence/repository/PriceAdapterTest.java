package com.kairos.ecommerce.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.kairos.ecommerce.domain.models.Price;
import com.kairos.ecommerce.infrastructure.persistence.daos.PricesJPARepository;
import com.kairos.ecommerce.infrastructure.persistence.entities.PriceEntity;
import com.kairos.ecommerce.infrastructure.persistence.mapper.PriceEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PriceAdapterTest {

    private PricesJPARepository pricesJPARepository;
    private PriceEntityMapper priceEntityMapper;
    private PriceAdapter priceAdapter;

    @BeforeEach
    void setUp() {
        pricesJPARepository = mock(PricesJPARepository.class);
        priceEntityMapper = mock(PriceEntityMapper.class);
        priceAdapter = new PriceAdapter(pricesJPARepository, priceEntityMapper);
    }

    @Test
    void shouldReturnMappedPricesWhenRepositoryReturnsEntities() {
        // given
        final var brandId = 1L;
        final var productId = 35455L;
        final var date = LocalDateTime.of(2020, 6, 14, 16, 0);

        final var entity1 = new PriceEntity(brandId, productId, 1L, 1L, 35.5f, "EUR", date.minusDays(1), date.plusDays(1));
        final var price1 = Price.builder()
                .brandId(brandId)
                .productId(productId)
                .priceList(1L)
                .priority(1L)
                .price(35.5f)
                .currency("EUR")
                .startDate(date.minusDays(1))
                .endDate(date.plusDays(1))
                .build();

        when(pricesJPARepository.findPriceByBrandIdAndProductIdAndDateBetweenStartDateAndEndDate(brandId, productId, date))
                .thenReturn(List.of(entity1));
        when(priceEntityMapper.priceEntityToPrice(entity1)).thenReturn(price1);

        // when
        final var result = priceAdapter.findPriceByBrandIdAndProductIdAndDateBetweenStartDateAndEndDate(brandId, productId, date);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(price1);

        // verify repository called with correct params
        ArgumentCaptor<Long> brandCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Long> productCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<LocalDateTime> dateCaptor = ArgumentCaptor.forClass(LocalDateTime.class);

        verify(pricesJPARepository).findPriceByBrandIdAndProductIdAndDateBetweenStartDateAndEndDate(
                brandCaptor.capture(), productCaptor.capture(), dateCaptor.capture());

        assertThat(brandCaptor.getValue()).isEqualTo(brandId);
        assertThat(productCaptor.getValue()).isEqualTo(productId);
        assertThat(dateCaptor.getValue()).isEqualTo(date);

        verify(priceEntityMapper).priceEntityToPrice(entity1);
    }

    @Test
    void shouldReturnEmptyListWhenRepositoryReturnsNoEntities() {
        // given
        final var brandId = 1L;
        final var productId = 35455L;
        final var date = LocalDateTime.of(2020, 6, 14, 16, 0);

        when(pricesJPARepository.findPriceByBrandIdAndProductIdAndDateBetweenStartDateAndEndDate(brandId, productId, date))
                .thenReturn(List.of());

        // when
        final var result = priceAdapter.findPriceByBrandIdAndProductIdAndDateBetweenStartDateAndEndDate(brandId, productId, date);

        // then
        assertThat(result).isEmpty();

        verify(pricesJPARepository).findPriceByBrandIdAndProductIdAndDateBetweenStartDateAndEndDate(
                brandId, productId, date);
    }
}
