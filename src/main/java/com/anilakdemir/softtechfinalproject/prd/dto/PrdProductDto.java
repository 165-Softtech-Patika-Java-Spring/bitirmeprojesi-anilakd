package com.anilakdemir.softtechfinalproject.prd.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author anilakdemir
 */
@Data
public class PrdProductDto {

    private Long id;

    private String name;

    private BigDecimal price;

    private Long productCategoryId;

    private BigDecimal taxPrice;

    private BigDecimal totalPrice;
}
