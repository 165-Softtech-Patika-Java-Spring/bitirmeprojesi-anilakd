package com.anilakdemir.softtechfinalproject.prd.service;

import com.anilakdemir.softtechfinalproject.gen.exceptions.DuplicateException;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductCategoryDetail;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductCategoryDto;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductCategorySaveRequestDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author anilakdemir
 */
@ExtendWith(MockitoExtension.class)
class PrdProductCategoryServiceTest {

    @InjectMocks
    private PrdProductCategoryService prdProductCategoryService;

    @Mock
    private PrdProductCategoryEntityService prdProductCategoryEntityService;

    @Mock
    private PrdProductEntityService prdProductEntityService;

    @Mock
    private PrdProductService prdProductService;

    @Test
    void shouldSave () {

        PrdProductCategoryType prdProductCategoryType = PrdProductCategoryType.FOOD;

        PrdProductCategorySaveRequestDto prdProductCategorySaveRequestDto = mock(PrdProductCategorySaveRequestDto.class);
        when(prdProductCategorySaveRequestDto.getCategoryType()).thenReturn(prdProductCategoryType);
        when(prdProductCategorySaveRequestDto.getTaxRate()).thenReturn(new BigDecimal(10));

        when(prdProductCategoryEntityService.existByCategoryType(any())).thenReturn(false);

        PrdProductCategory prdProductCategory = mock(PrdProductCategory.class);
        when(prdProductCategory.getId()).thenReturn(1L);
        when(prdProductCategoryEntityService.save(any())).thenReturn(prdProductCategory);

        PrdProductCategoryDto prdProductCategoryDto = prdProductCategoryService.save(prdProductCategorySaveRequestDto);

        assertEquals(prdProductCategoryDto.getId(), 1L);
    }

    @Test
    void shouldNotSave_whenCategoryIsExist () {

        PrdProductCategoryType prdProductCategoryType = PrdProductCategoryType.FOOD;

        PrdProductCategorySaveRequestDto prdProductCategorySaveRequestDto = mock(PrdProductCategorySaveRequestDto.class);
        when(prdProductCategorySaveRequestDto.getCategoryType()).thenReturn(prdProductCategoryType);

        when(prdProductCategoryEntityService.existByCategoryType(any())).thenReturn(true);

        assertThrows(DuplicateException.class, () -> prdProductCategoryService.save(prdProductCategorySaveRequestDto));
    }

    @Test
    void shouldNotSave_whenSaveRequestIsNull () {

        assertThrows(NullPointerException.class, () -> prdProductCategoryService.save(null));
    }

    @Test
    void shouldUpdatePrice () {

        BigDecimal taxRate = new BigDecimal(0.15);

        PrdProductCategoryType prdProductCategoryType = PrdProductCategoryType.FOOD;

        PrdProductCategory prdProductCategory = mock(PrdProductCategory.class);
        when(prdProductCategory.getId()).thenReturn(1L);
        when(prdProductCategoryEntityService.findByCategoryType(prdProductCategoryType)).thenReturn(prdProductCategory);

        when(prdProductCategoryEntityService.save(any())).thenReturn(prdProductCategory);
        List<PrdProduct> prdProductList = new ArrayList<>();
        PrdProduct prdProduct = mock(PrdProduct.class);
        prdProductList.add(prdProduct);

        when(prdProductEntityService.findAllByProductsCategoryId(prdProductCategory.getId())).thenReturn(prdProductList);

        prdProductCategoryService.updatePrice(taxRate, prdProductCategoryType);
        verify(prdProductService).saveAll(any());
    }

    @Test
    void shouldFindAllProductWithDetail () {

        PrdProductCategoryDetail prdProductCategoryDetail = mock(PrdProductCategoryDetail.class);
        List<PrdProductCategoryDetail> prdProductCategoryDetailList = new ArrayList<>();
        prdProductCategoryDetailList.add(prdProductCategoryDetail);

        when(prdProductCategoryEntityService.findAllProductWithDetail()).thenReturn(prdProductCategoryDetailList);

        List<PrdProductCategoryDetail> productCategoryDetailList = prdProductCategoryService.findAllProductWithDetail();

        assertEquals(productCategoryDetailList.size(), 1);
    }
}