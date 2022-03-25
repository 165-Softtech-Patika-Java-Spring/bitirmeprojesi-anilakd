package com.anilakdemir.softtechfinalproject.prd.controller;

import com.anilakdemir.softtechfinalproject.gen.dto.RestResponse;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductDto;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductSaveRequestDto;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductUpdateRequestDto;
import com.anilakdemir.softtechfinalproject.prd.enums.PrdProductCategoryType;
import com.anilakdemir.softtechfinalproject.prd.service.PrdProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author anilakdemir
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Validated
public class PrdProductController {

    private final PrdProductService prdProductService;

    @Operation(
            tags = "Product Controller",
            description = "Creates new product",
            summary = "Save product",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Products",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = PrdProductSaveRequestDto.class
                                    ),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Strawberry Product Example",
                                                    summary = "New Product for FOOD",
                                                    value = "{\n" +
                                                            "  \"categoryType\": \"FOOD\",\n" +
                                                            "  \"name\": \"Strawberry\",\n" +
                                                            "  \"price\": \"15\"\n" +
                                                            "}"
                                            ),
                                            @ExampleObject(
                                                    name = "T-Shirt Product Example",
                                                    summary = "New Product for CLOTHES",
                                                    value = "{\n" +
                                                            "  \"categoryType\": \"CLOTHES\",\n" +
                                                            "  \"name\": \"T-Shirt\",\n" +
                                                            "  \"price\": \"300\"\n" +
                                                            "}"
                                            )
                                    }
                            ),
                    }
            )
    )
    @PostMapping
    public ResponseEntity save (@Valid @RequestBody PrdProductSaveRequestDto prdProductSaveRequestDto) {

        PrdProductDto prdProductDto = prdProductService.save(prdProductSaveRequestDto);

        WebMvcLinkBuilder linkDelete = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(
                        this.getClass()).deleteById(prdProductDto.getId()));

        EntityModel entityModel = EntityModel.of(prdProductDto);

        entityModel.add(linkDelete.withRel("delete"));

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(entityModel);

        return ResponseEntity.ok(RestResponse.of(mappingJacksonValue));
    }

    @Operation(tags = "Product Controller", summary = "Update product")
    @PutMapping
    public ResponseEntity update (@RequestBody PrdProductUpdateRequestDto prdProductUpdateRequestDto) {

        PrdProductDto prdProductDto = prdProductService.update(prdProductUpdateRequestDto);

        return ResponseEntity.ok(RestResponse.of(prdProductDto));
    }

    @Operation(tags = "Product Controller", summary = "Delete product by id")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById (@PathVariable Long id) {

        prdProductService.deleteById(id);

        return ResponseEntity.ok(RestResponse.empty());
    }

    @Operation(tags = "Product Controller", summary = "Update price by product id")
    @PatchMapping("/price/{id}")
    public ResponseEntity updatePrice (@PathVariable Long id,
                                       @RequestParam @DecimalMin(value = "0.0", message = "Price can not be negative") BigDecimal price) {

        PrdProductDto prdProductDto = prdProductService.updatePrice(id, price);

        return ResponseEntity.ok(RestResponse.of(prdProductDto));
    }

    @Operation(tags = "Product Controller", summary = "Get all products for selected category")
    @GetMapping("/category")
    public ResponseEntity findAllByCategoryType (@RequestParam PrdProductCategoryType prdProductCategoryType) {

        List<PrdProductDto> prdProductDtoList = prdProductService.findAllByCategoryType(prdProductCategoryType);

        return ResponseEntity.ok(RestResponse.of(prdProductDtoList));
    }

    @Operation(tags = "Product Controller", summary = "Get products between min and max prices")
    @GetMapping("/filter/by/price")
    public ResponseEntity findAllByTotalPriceBetween (@RequestParam BigDecimal min, @RequestParam BigDecimal max) {

        List<PrdProductDto> prdProductDtoList = prdProductService.findAllByTotalPriceBetween(min, max);

        return ResponseEntity.ok(RestResponse.of(prdProductDtoList));
    }
}