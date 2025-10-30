package com.kairos.ecommerce.infrastructure.rest.controller;

import java.time.OffsetDateTime;

import com.kairos.ecommerce.application.usecases.GetProductPriceByDateUseCase;
import com.kairos.ecommerce.infrastructure.rest.api.PriceApi;
import com.kairos.ecommerce.infrastructure.rest.dto.PriceDTO;
import com.kairos.ecommerce.infrastructure.rest.mapper.PriceDTOMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class PriceController implements PriceApi {

    private GetProductPriceByDateUseCase getProductPriceByDateUseCase;
    private PriceDTOMapper priceDTOMapper;

    @Override
    public ResponseEntity<PriceDTO> getProductPriceByDate(Long brandId, Long productId, OffsetDateTime applicationDate) {
        log.info("Getting price by date");
        final var price = getProductPriceByDateUseCase.getProductPriceByDateWithHighestPriority(brandId, productId,
                applicationDate.toLocalDateTime());
        final var productDTO = priceDTOMapper.toDTO(price);
        return ResponseEntity.ok(productDTO);
    }
}
