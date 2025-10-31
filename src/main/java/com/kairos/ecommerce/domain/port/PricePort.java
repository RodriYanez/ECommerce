package com.kairos.ecommerce.domain.port;

import java.time.LocalDateTime;
import java.util.List;

import com.kairos.ecommerce.domain.models.Price;

public interface PricePort {
    List<Price> findPriceByBrandIdAndProductIdAndDateBetweenStartDateAndEndDate(Long brandId, Long productId, LocalDateTime date);
}
