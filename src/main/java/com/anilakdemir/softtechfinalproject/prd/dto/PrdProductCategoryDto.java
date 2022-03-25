package com.anilakdemir.softtechfinalproject.prd.dto;

import com.anilakdemir.softtechfinalproject.prd.enums.PrdProductCategoryType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author anilakdemir
 */
@Data
public class PrdProductCategoryDto {

    private Long id;

    private PrdProductCategoryType categoryType;

    private BigDecimal taxRate;
}
