package com.anilakdemir.softtechfinalproject.prd.dto;

import com.anilakdemir.softtechfinalproject.prd.enums.PrdProductCategoryType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

/**
 * @author anilakdemir
 */
@Data
public class PrdProductUpdateRequestDto {

    @NotNull(message = "Id can not be null")
    private Long id;

    @NotBlank(message = "Name can not be blank")
    @NotNull(message = "Name can not be null")
    private String name;

    @NotNull(message = "Category type can not be null")
    private PrdProductCategoryType categoryType;

    @NotNull(message = "Price can not be null")
    @PositiveOrZero(message = "Price should be zero or positive")
    private BigDecimal price;

}
