package com.anilakdemir.softtechfinalproject.prd.dto;

import com.anilakdemir.softtechfinalproject.prd.enums.PrdProductCategoryType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * @author anilakdemir
 */
@Data
public class PrdProductSaveRequestDto {

    @NotBlank(message = "Name can not be blank")
    @NotNull(message = "Product name can not be null")
    private String name;

    @NotNull(message = "Category type can not be null")
    private PrdProductCategoryType categoryType;

    @Positive(message = "Price should be positive")
    @NotNull(message = "Price can not be null")
    private BigDecimal price;
}