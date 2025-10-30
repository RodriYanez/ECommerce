package com.kairos.ecommerce.domain.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Price {
    private Long brandId;
    private Long productId;
    private Long priceList;
    private Long priority;
    private float price;
    private String currency;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
