package com.kairos.ecommerce.infrastructure.rest.controller;

import java.time.OffsetDateTime;

import com.kairos.ecommerce.application.usecases.GetProductPriceByDateUseCase;
import com.kairos.ecommerce.infrastructure.rest.api.PriceApi;
import com.kairos.ecommerce.infrastructure.rest.dto.PriceDTO;
import com.kairos.ecommerce.infrastructure.rest.mapper.PriceDTOMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PriceController implements PriceApi {

    private GetProductPriceByDateUseCase getProductPriceByDateUseCase;
    private PriceDTOMapper priceDTOMapper;

    @Override
    public ResponseEntity<PriceDTO> getProductPriceByDate(Long brandId, Long productId, OffsetDateTime applicationDate) {
        final var price = getProductPriceByDateUseCase.getProductPriceByDateWithHighestPriority(brandId, productId,
                applicationDate.toLocalDateTime());
        final var productDTO = priceDTOMapper.toDTO(price);
        return ResponseEntity.ok(productDTO);
    }
}
