package com.kairos.ecommerce.infrastructure.rest.controller;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.kairos.ecommerce.application.usecases.GetProductPriceByDateUseCase;
import com.kairos.ecommerce.domain.models.Price;
import com.kairos.ecommerce.infrastructure.rest.dto.PriceDTO;
import com.kairos.ecommerce.infrastructure.rest.mapper.PriceDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class PriceControllerTest {

    @Mock
    private GetProductPriceByDateUseCase getProductPriceByDateUseCase;

    @Mock
    private PriceDTOMapper priceDTOMapper;

    @InjectMocks
    private PriceController priceController;

    private Long brandId;
    private Long productId;
    private OffsetDateTime applicationDate;

    private Price domainPrice;
    private PriceDTO priceDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        brandId = 1L;
        productId = 35455L;
        applicationDate = OffsetDateTime.of(2020, 6, 14, 16, 0, 0, 0, ZoneOffset.UTC);

        domainPrice = Price.builder()
                .brandId(brandId)
                .productId(productId)
                .priceList(1L)
                .price(35.50f)
                .currency("EUR")
                .priority(0L)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .build();

        priceDTO = PriceDTO.builder()
                .brandId(brandId)
                .productId(productId)
                .tariffId(1L)
                .price(35.50f)
                .currency("EUR")
                .build();
    }

    @Test
    void shouldReturnPriceDTOWhenPriceExists() {
        // given
        when(getProductPriceByDateUseCase.getProductPriceByDateWithHighestPriority(brandId, productId, applicationDate.toLocalDateTime()))
                .thenReturn(domainPrice);

        when(priceDTOMapper.toDTO(domainPrice)).thenReturn(priceDTO);

        // when
        final var response = priceController.getProductPriceByDate(
                brandId, productId, applicationDate);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(priceDTO);

        verify(getProductPriceByDateUseCase).getProductPriceByDateWithHighestPriority(
                brandId, productId, applicationDate.toLocalDateTime());
        verify(priceDTOMapper).toDTO(domainPrice);
    }

    @Test
    void shouldPropagateExceptionWhenUseCaseThrowsError() {
        // given
        when(getProductPriceByDateUseCase.getProductPriceByDateWithHighestPriority(any(), any(), any()))
                .thenThrow(new RuntimeException("Unexpected error"));

        // when / then

        assertThatThrownBy(() -> priceController.getProductPriceByDate(brandId, productId, applicationDate))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Unexpected error");

        verify(getProductPriceByDateUseCase).getProductPriceByDateWithHighestPriority(
                brandId, productId, applicationDate.toLocalDateTime());
        verifyNoInteractions(priceDTOMapper);
    }
}

