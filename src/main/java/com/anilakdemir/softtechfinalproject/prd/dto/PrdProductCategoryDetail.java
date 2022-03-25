package com.anilakdemir.softtechfinalproject.prd.dto;

import com.anilakdemir.softtechfinalproject.prd.enums.PrdProductCategoryType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author anilakdemir
 */

@Data
@AllArgsConstructor
public class PrdProductCategoryDetail {

    private PrdProductCategoryType prdProductCategoryType;

    private BigDecimal taxRate;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private Double averagePrice;

    private Long count;
}
