package com.kairos.ecommerce.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.kairos.ecommerce.domain.models.Price;
import com.kairos.ecommerce.domain.port.PricePort;
import com.kairos.ecommerce.infrastructure.exceptions.PriceNotFoundException;
import com.kairos.ecommerce.infrastructure.persistence.daos.PricesJPARepository;
import com.kairos.ecommerce.infrastructure.persistence.mapper.PriceEntityMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
@Slf4j
public class PriceAdapter implements PricePort {
    private PricesJPARepository pricesJPARepository;
    private PriceEntityMapper priceEntityMapper;

    @Override
    public List<Price> findPriceByBrandIdAndProductIdAndDateOrderByPriorityDesc(Long brandId, Long productId, LocalDateTime date) {
        final var entities = pricesJPARepository.findPriceByBrandIdAndProductIdAndDateOrderByPriorityDesc(brandId, productId, date);

        if (entities == null || entities.isEmpty()) {
            final var message = "No prices found for brandId=%d, productId=%d, date=%s".formatted(brandId, productId, date);
            log.warn(message);
            throw new PriceNotFoundException(message);
        }

        log.info("Found %d prices".formatted(entities.size()));
        return entities.stream()
                .map(priceEntityMapper::priceEntityToPrice)
                .toList();
    }
}
