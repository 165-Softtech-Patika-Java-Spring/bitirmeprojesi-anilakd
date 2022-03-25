package com.anilakdemir.softtechfinalproject.prd.service;

import com.anilakdemir.softtechfinalproject.gen.exceptions.DuplicateException;
import com.anilakdemir.softtechfinalproject.prd.converter.PrdProductCategoryMapper;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductCategoryDetail;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductCategoryDto;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductCategorySaveRequestDto;
import com.anilakdemir.softtechfinalproject.prd.entity.PrdProduct;
import com.anilakdemir.softtechfinalproject.prd.entity.PrdProductCategory;
import com.anilakdemir.softtechfinalproject.prd.enums.PrdProductCategoryType;
import com.anilakdemir.softtechfinalproject.prd.exceptions.PrdProductCategoryErrorMessage;
import com.anilakdemir.softtechfinalproject.prd.service.entityService.PrdProductCategoryEntityService;
import com.anilakdemir.softtechfinalproject.prd.service.entityService.PrdProductEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author anilakdemir
 */
@Service
@RequiredArgsConstructor
public class PrdProductCategoryService {

    private final PrdProductCategoryEntityService prdProductCategoryEntityService;
    private final PrdProductEntityService prdProductEntityService;
    private final PrdProductService prdProductService;

    public PrdProductCategoryDto save (PrdProductCategorySaveRequestDto prdProductCategorySaveRequestDto) {

        PrdProductCategoryType categoryType = prdProductCategorySaveRequestDto.getCategoryType();

        boolean isExistCategoryType = prdProductCategoryEntityService.existByCategoryType(categoryType);

        if (isExistCategoryType){
            throw new DuplicateException(PrdProductCategoryErrorMessage.PRODUCT_CATEGORY_IS_ALREADY_EXIST);
        }

        PrdProductCategory prdProductCategory = PrdProductCategoryMapper.INSTANCE.convertToPrdProductCategory(prdProductCategorySaveRequestDto);

        prdProductCategory = prdProductCategoryEntityService.save(prdProductCategory);

        PrdProductCategoryDto prdProductCategoryDto = PrdProductCategoryMapper.INSTANCE.convertToPrdProductCategoryDto(prdProductCategory);

        return prdProductCategoryDto;
    }

    /**
     * When category type tax rate changed, products will be updated.
     * It needs transaction
     */
    @Transactional()
    public void updatePrice (BigDecimal taxRate, PrdProductCategoryType prdProductCategoryType) {

        PrdProductCategory productCategory = prdProductCategoryEntityService.findByCategoryType(prdProductCategoryType);

        Long categoryTypeId = productCategory.getId();

        productCategory.setTaxRate(taxRate);
        prdProductCategoryEntityService.save(productCategory);

        List<PrdProduct> prdProductList = prdProductEntityService.findAllByProductsCategoryId(categoryTypeId);

        for (PrdProduct prdProduct : prdProductList) {
            prdProductService.calculatePriceForProduct(prdProduct, productCategory);
        }

        prdProductService.saveAll(prdProductList);
    }

    public List<PrdProductCategoryDetail> findAllProductWithDetail () {

        List<PrdProductCategoryDetail> prdProductCategoryDetailList = prdProductCategoryEntityService.findAllProductWithDetail();

        controlCategoryHasProduct(prdProductCategoryDetailList);

        return prdProductCategoryDetailList;
    }

    /**
     * If there is no product in a category, we do not want to give null fields
     * So, some parameters manually added.
     */
    private void controlCategoryHasProduct (List<PrdProductCategoryDetail> prdProductCategoryDetailList) {
        for (PrdProductCategoryDetail prdProductCategoryDetail : prdProductCategoryDetailList) {
            if (prdProductCategoryDetail.getCount().equals(0L)){
                prdProductCategoryDetail.setMinPrice(BigDecimal.ZERO);
                prdProductCategoryDetail.setMaxPrice(BigDecimal.ZERO);
                prdProductCategoryDetail.setAveragePrice(0D);
            }
        }
    }
}
