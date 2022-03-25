package com.anilakdemir.softtechfinalproject.prd.service;

import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductDto;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductSaveRequestDto;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductUpdateRequestDto;
import com.anilakdemir.softtechfinalproject.prd.entity.PrdProduct;
import com.anilakdemir.softtechfinalproject.prd.entity.PrdProductCategory;
import com.anilakdemir.softtechfinalproject.prd.enums.PrdProductCategoryType;
import com.anilakdemir.softtechfinalproject.prd.service.entityService.PrdProductCategoryEntityService;
import com.anilakdemir.softtechfinalproject.prd.service.entityService.PrdProductEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author anilakdemir
 */
@ExtendWith(MockitoExtension.class)
class PrdProductServiceTest {

    @InjectMocks
    private PrdProductService prdProductService;

    @Mock
    private PrdProductEntityService prdProductEntityService;

    @Mock
    private PrdProductCategoryEntityService prdProductCategoryEntityService;

    @Test
    void shouldSave () {

        PrdProductSaveRequestDto prdProductSaveRequestDto = mock(PrdProductSaveRequestDto.class);
        when(prdProductSaveRequestDto.getPrice()).thenReturn(new BigDecimal(100));
        when(prdProductSaveRequestDto.getCategoryType()).thenReturn(PrdProductCategoryType.FOOD);

        PrdProduct prdProduct = mock(PrdProduct.class);
        when(prdProduct.getId()).thenReturn(1L);

        PrdProductCategory prdProductCategory = mock(PrdProductCategory.class);
        when(prdProductCategory.getTaxRate()).thenReturn(new BigDecimal(100));

        when(prdProductCategoryEntityService.findByCategoryType(any())).thenReturn(prdProductCategory);
        when(prdProductEntityService.save(any())).thenReturn(prdProduct);

        PrdProductDto result = prdProductService.save(prdProductSaveRequestDto);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldUpdate () {
        PrdProductUpdateRequestDto prdProductUpdateRequestDto = mock(PrdProductUpdateRequestDto.class);
        when(prdProductUpdateRequestDto.getId()).thenReturn(1L);
        when(prdProductUpdateRequestDto.getPrice()).thenReturn(new BigDecimal(100));

        when(prdProductEntityService.existById(anyLong())).thenReturn(true);
        when(prdProductUpdateRequestDto.getCategoryType()).thenReturn(PrdProductCategoryType.FOOD);

        PrdProductCategory prdProductCategory = mock(PrdProductCategory.class);
        when(prdProductCategory.getId()).thenReturn(1L);
        when(prdProductCategory.getTaxRate()).thenReturn(new BigDecimal("0.18"));

        when(prdProductCategoryEntityService.findByCategoryType(any())).thenReturn(prdProductCategory);

        PrdProduct prdProduct = mock(PrdProduct.class);
        when(prdProduct.getId()).thenReturn(2L);

        when(prdProductEntityService.save(any())).thenReturn(prdProduct);

        PrdProductDto result = prdProductService.update(prdProductUpdateRequestDto);

        assertEquals(prdProduct.getId(), result.getId());
        assertEquals(prdProduct.getTotalPrice(), result.getTotalPrice());
        assertEquals(prdProduct.getProductCategoryId(), result.getProductCategoryId());


    }

    @Test
    void shouldDeleteById () {

        Long id = 1L;

        when(prdProductEntityService.existById(id)).thenReturn(true);
        doNothing().when(prdProductEntityService).deleteById(id);

        prdProductService.deleteById(id);

        verify(prdProductEntityService).deleteById(id);
    }

    @Test
    void shouldNotDeleteById_whenPriceIsNull () {

        assertThrows(NullPointerException.class, () -> prdProductService.update(null));
    }

    @Test
    void shouldUpdatePrice () {

        Long id = 1L;
        Long categoryId = 2L;
        BigDecimal price = new BigDecimal(100);

        when(prdProductEntityService.existById(id)).thenReturn(true);

        PrdProduct prdProduct = mock(PrdProduct.class);
        when(prdProduct.getProductCategoryId()).thenReturn(categoryId);
        when(prdProduct.getPrice()).thenReturn(new BigDecimal(200));

        PrdProduct updatedProduct = mock(PrdProduct.class);
        when(updatedProduct.getId()).thenReturn(id);
        when(updatedProduct.getProductCategoryId()).thenReturn(categoryId);
        when(updatedProduct.getPrice()).thenReturn(new BigDecimal(100));

        PrdProductCategory prdProductCategory = mock(PrdProductCategory.class);
        when(prdProductCategory.getTaxRate()).thenReturn(new BigDecimal("0.7"));

        when(prdProductEntityService.findById(anyLong())).thenReturn(prdProduct);
        when(prdProductEntityService.save(any())).thenReturn(updatedProduct);

        when(prdProductCategoryEntityService.findById(anyLong())).thenReturn(prdProductCategory);

        PrdProductDto result = prdProductService.updatePrice(id, price);

        assertNotEquals(result.getPrice(), prdProduct.getPrice());
    }

    @Test
    void shouldFindAllByCategoryType () {

        PrdProductCategory prdProductCategory = mock(PrdProductCategory.class);
        when(prdProductCategory.getId()).thenReturn(1L);

        PrdProductCategoryType productCategoryType = PrdProductCategoryType.FOOD;

        when(prdProductCategoryEntityService.findByCategoryType(productCategoryType)).thenReturn(prdProductCategory);

        PrdProduct prdProduct = mock(PrdProduct.class);
        List<PrdProduct> prdProductList = new ArrayList<>();
        prdProductList.add(prdProduct);

        when(prdProductEntityService.findAllByProductsCategoryId(anyLong())).thenReturn(prdProductList);

        List<PrdProductDto> prdProductDtoList = prdProductService.findAllByCategoryType(productCategoryType);

        assertEquals(prdProductList.size(), prdProductDtoList.size());
    }

    @Test
    void shouldFindAllByTotalPriceBetween () {

        PrdProduct prdProduct = mock(PrdProduct.class);
        List<PrdProduct> prdProductList = new ArrayList<>();
        prdProductList.add(prdProduct);

        when(prdProductEntityService.findAllByTotalPriceBetween(any(), any())).thenReturn(prdProductList);

        List<PrdProductDto> resultList = prdProductService.findAllByTotalPriceBetween(new BigDecimal(100), new BigDecimal(200));

        assertEquals(resultList.size(), prdProductList.size());
    }

    @Test
    void shouldCalculatePriceForProduct () {

        PrdProduct prdProduct = mock(PrdProduct.class);
        when(prdProduct.getPrice()).thenReturn(new BigDecimal(100));
        when(prdProduct.getTotalPrice()).thenReturn(new BigDecimal(115));

        PrdProductCategory prdProductCategory = mock(PrdProductCategory.class);
        when(prdProductCategory.getTaxRate()).thenReturn(new BigDecimal("0.15"));

        prdProductService.calculatePriceForProduct(prdProduct, prdProductCategory);

        assertEquals(prdProduct.getTotalPrice(), new BigDecimal(115));

    }

    @Test
    void shouldSaveAll () {

        PrdProduct prdProduct = mock(PrdProduct.class);

        List<PrdProduct> prdProductList = new ArrayList<>();
        prdProductList.add(prdProduct);

        prdProductService.saveAll(prdProductList);

        verify(prdProductEntityService).saveAll(prdProductList);
    }

    @Test
    void shouldNotSaveAll_whenListParameterIsNull () {
        assertThrows(NullPointerException.class, () -> prdProductService.update(null));
    }
}