package com.kairos.ecommerce.infrastructure.persistence.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
@Table(name = "prices")
@IdClass(PriceId.class)
public class PriceEntity {
    @Id
    @Column(name = "brand_id")
    private Long brandId;
    @Id
    @Column(name = "product_id")
    private Long productId;
    @Id
    @Column(name = "price_list")
    private Long priceList;

    @Column(name = "priority")
    private Long priority;

    @Column(name = "price")
    private float price;

    @Column(name = "curr")
    private String currency;

    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;
}
