package com.kairos.ecommerce.application.usecases;

import java.time.LocalDateTime;
import java.util.List;

import com.kairos.ecommerce.domain.models.Price;
import com.kairos.ecommerce.domain.port.PricePort;
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
        final var prices = pricePort.findPriceByBrandIdAndProductIdAndDateOrderByPriorityDesc(brandId, productId, date);
        return getPriceWithHighestPriority(prices);
    }

    private Price getPriceWithHighestPriority(List<Price> prices) {
        //As far as the query already order by priority, we only need to get the first one here.
        return prices.get(0);
    }
}
