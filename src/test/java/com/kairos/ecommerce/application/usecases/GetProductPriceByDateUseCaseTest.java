package com.kairos.ecommerce.application.usecases;

import java.time.LocalDateTime;
import java.util.List;

import com.kairos.ecommerce.domain.models.Price;
import com.kairos.ecommerce.domain.port.PricePort;
import com.kairos.ecommerce.infrastructure.exceptions.PriceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetProductPriceByDateUseCaseTest {

    @Mock
    private PricePort pricePort;

    @InjectMocks
    private GetProductPriceByDateUseCase useCase;

    private Long brandId;
    private Long productId;
    private LocalDateTime date;

    private Price priceLowPriority;
    private Price priceHighPriority;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        brandId = 1L;
        productId = 35455L;
        date = LocalDateTime.of(2020, 6, 14, 16, 0);

        priceLowPriority = Price.builder()
                .brandId(brandId)
                .productId(productId)
                .priceList(1L)
                .priority(0L)
                .price(25.45f)
                .currency("EUR")
                .build();

        priceHighPriority = Price.builder()
                .brandId(brandId)
                .productId(productId)
                .priceList(2L)
                .priority(1L)
                .price(35.50f)
                .currency("EUR")
                .build();
    }

    @Test
    void shouldReturnFirstPriceFromPort() {
        // given
        when(pricePort.findPriceByBrandIdAndProductIdAndDateBetweenStartDateAndEndDate(brandId, productId, date))
                .thenReturn(List.of(priceHighPriority, priceLowPriority));

        // when
        final var result = useCase.getProductPriceByDateWithHighestPriority(brandId, productId, date);

        // then
        assertThat(result).isEqualTo(priceHighPriority);
        verify(pricePort).findPriceByBrandIdAndProductIdAndDateBetweenStartDateAndEndDate(brandId, productId, date);
    }

    @Test
    void shouldPropagateExceptionWhenPortFails() {
        // given
        when(pricePort.findPriceByBrandIdAndProductIdAndDateBetweenStartDateAndEndDate(any(), any(), any()))
                .thenThrow(new PriceNotFoundException("Price not found."));

        // when / then
        assertThatThrownBy(() -> useCase.getProductPriceByDateWithHighestPriority(brandId, productId, date))
                .isInstanceOf(PriceNotFoundException.class)
                .hasMessage("Price not found.");

        verify(pricePort).findPriceByBrandIdAndProductIdAndDateBetweenStartDateAndEndDate(brandId, productId, date);
    }

    @Test
    void shouldThrowExceptionWhenRepositoryReturnsEmptyList() {
        // given
        when(pricePort.findPriceByBrandIdAndProductIdAndDateBetweenStartDateAndEndDate(brandId, productId, date))
                .thenReturn(List.of());

        // when / then
        assertThatThrownBy(() ->
                useCase.getProductPriceByDateWithHighestPriority(brandId, productId, date))
                .isInstanceOf(PriceNotFoundException.class)
                .hasMessageContaining("No prices found for brandId=%d, productId=%d".formatted(brandId, productId));

        verify(pricePort).findPriceByBrandIdAndProductIdAndDateBetweenStartDateAndEndDate(brandId, productId, date);
    }

    @Test
    void shouldThrowExceptionWhenRepositoryReturnsNull() {
        // given
        when(pricePort.findPriceByBrandIdAndProductIdAndDateBetweenStartDateAndEndDate(brandId, productId, date))
                .thenReturn(null);

        // when / then
        assertThatThrownBy(() ->
                useCase.getProductPriceByDateWithHighestPriority(brandId, productId, date))
                .isInstanceOf(PriceNotFoundException.class)
                .hasMessageContaining("No prices found for brandId=%d, productId=%d".formatted(brandId, productId));

        verify(pricePort).findPriceByBrandIdAndProductIdAndDateBetweenStartDateAndEndDate(brandId, productId, date);
    }
}
