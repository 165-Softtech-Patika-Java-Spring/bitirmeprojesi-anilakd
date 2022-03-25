package com.anilakdemir.softtechfinalproject.prd.dto;

import com.anilakdemir.softtechfinalproject.prd.enums.PrdProductCategoryType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

/**
 * @author anilakdemir
 */
@Data
public class PrdProductCategorySaveRequestDto {

    @NotNull(message = "Category type can not be null")
    private PrdProductCategoryType categoryType;

    @PositiveOrZero(message = "Tax rate should be zero or positive")
    private BigDecimal taxRate;
}
