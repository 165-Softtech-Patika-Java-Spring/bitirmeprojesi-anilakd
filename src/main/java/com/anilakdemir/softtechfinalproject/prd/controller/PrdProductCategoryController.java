package com.anilakdemir.softtechfinalproject.prd.controller;

import com.anilakdemir.softtechfinalproject.gen.dto.RestResponse;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductCategoryDto;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductCategorySaveRequestDto;
import com.anilakdemir.softtechfinalproject.prd.enums.PrdProductCategoryType;
import com.anilakdemir.softtechfinalproject.prd.service.PrdProductCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

/**
 * @author anilakdemir
 */
@RestController
@RequestMapping("/api/v1/product-categories")
@RequiredArgsConstructor
@Validated
public class PrdProductCategoryController {

    private final PrdProductCategoryService prdProductCategoryService;

    @Operation(tags = "Product Category Controller", summary = "Save product category")
    @PostMapping
    public ResponseEntity save (@Valid @RequestBody PrdProductCategorySaveRequestDto prdProductCategorySaveRequestDto) {

        PrdProductCategoryDto prdProductCategoryDto = prdProductCategoryService.save(prdProductCategorySaveRequestDto);

        return ResponseEntity.ok(RestResponse.of(prdProductCategoryDto));
    }

    @Operation(tags = "Product Category Controller", summary = "Update product price")
    @PatchMapping
    public ResponseEntity updatePrice (@RequestParam @DecimalMin(value = "0", message = "Tax rate can not be negative") BigDecimal taxRate,
                                       @RequestParam PrdProductCategoryType categoryType) {
        prdProductCategoryService.updatePrice(taxRate, categoryType);

        return ResponseEntity.ok(RestResponse.empty());
    }

    @Operation(tags = "Product Category Controller", summary = "Get category details")
    @GetMapping("/details")
    public ResponseEntity findAllProductWithDetail () {

        return ResponseEntity.ok(RestResponse.of(prdProductCategoryService.findAllProductWithDetail()));
    }
}
