package com.kairos.ecommerce.infrastructure.persistence.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class PriceId implements Serializable {
    private Long brandId;
    private Long productId;
    private Long priceList;
}
