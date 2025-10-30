package com.kairos.ecommerce.application.usecases;

import java.time.LocalDateTime;
import java.util.List;

import com.kairos.ecommerce.domain.models.Price;
import com.kairos.ecommerce.domain.port.PricePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetProductPriceByDateUseCase {
    private PricePort pricePort;

    public Price getProductPriceByDate(Long brandId, Long productId, LocalDateTime date) {
        final var prices = pricePort.findPriceByBrandIdAndProductIdAndDate(brandId, productId, date);
        return getPriceWithHighestPriority(prices);
    }

    private Price getPriceWithHighestPriority(List<Price> prices) {
        //As far as the query already order by priority, we only need to get the first one here.
        return prices.stream().findFirst().orElseThrow();
    }
}
