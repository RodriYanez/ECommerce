package com.kairos.ecommerce.infrastructure.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(String message) {
        super(message);
    }
}
