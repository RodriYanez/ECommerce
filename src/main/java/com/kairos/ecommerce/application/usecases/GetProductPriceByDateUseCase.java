package com.kairos.ecommerce.application.usecases;

import java.time.LocalDateTime;
import java.util.Comparator;

import com.kairos.ecommerce.domain.models.Price;
import com.kairos.ecommerce.domain.port.PricePort;
import com.kairos.ecommerce.infrastructure.exceptions.PriceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class GetProductPriceByDateUseCase {
    private PricePort pricePort;

    public Price getProductPriceByDateWithHighestPriority(Long brandId, Long productId, LocalDateTime date) {
        log.info("Getting price by date with highest priority");
        final var prices = pricePort.findPriceByBrandIdAndProductIdAndDateBetweenStartDateAndEndDate(brandId, productId, date);

        if (prices == null) {
            throw createPriceNotFoundException(brandId, productId, date);
        }

        return prices.stream()
                .max(Comparator.comparing(Price::getPriority))
                .orElseThrow(() -> createPriceNotFoundException(brandId, productId, date));
    }

    private PriceNotFoundException createPriceNotFoundException(Long brandId, Long productId, LocalDateTime date) {
        final var message = "No prices found for brandId=%d, productId=%d, date=%s".formatted(brandId, productId, date);
        log.warn(message);
        return new PriceNotFoundException(message);
    }
}
