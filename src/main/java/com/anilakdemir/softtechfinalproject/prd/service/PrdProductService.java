package com.anilakdemir.softtechfinalproject.prd.service;

import com.anilakdemir.softtechfinalproject.gen.exceptions.EntityNotFoundException;
import com.anilakdemir.softtechfinalproject.prd.converter.PrdProductMapper;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductDto;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductSaveRequestDto;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductUpdateRequestDto;
import com.anilakdemir.softtechfinalproject.prd.entity.PrdProduct;
import com.anilakdemir.softtechfinalproject.prd.entity.PrdProductCategory;
import com.anilakdemir.softtechfinalproject.prd.enums.PrdProductCategoryType;
import com.anilakdemir.softtechfinalproject.prd.exceptions.PrdProductErrorMessage;
import com.anilakdemir.softtechfinalproject.prd.service.entityService.PrdProductCategoryEntityService;
import com.anilakdemir.softtechfinalproject.prd.service.entityService.PrdProductEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author anilakdemir
 */
@Service
@RequiredArgsConstructor
public class PrdProductService {

    private final PrdProductEntityService prdProductEntityService;
    private final PrdProductCategoryEntityService prdProductCategoryEntityService;

    /**
     * NOT : We want to take category type as enum
     */
    public PrdProductDto save (PrdProductSaveRequestDto prdProductSaveRequestDto) {

        PrdProduct prdProduct = PrdProductMapper.INSTANCE.convertToPrdProduct(prdProductSaveRequestDto);

        PrdProductCategoryType categoryType = prdProductSaveRequestDto.getCategoryType();

        PrdProductCategory productCategory = getPrdProductCategory(categoryType);

        Long categoryId = productCategory.getId();

        prdProduct.setProductCategoryId(categoryId);

        BigDecimal price = prdProductSaveRequestDto.getPrice();

        setNewPrices(prdProduct, price, productCategory);

        prdProduct = prdProductEntityService.save(prdProduct);

        PrdProductDto prdProductDto = PrdProductMapper.INSTANCE.convertToPrdProductDto(prdProduct);

        return prdProductDto;
    }

    public PrdProductDto update (PrdProductUpdateRequestDto prdProductUpdateRequestDto) {

        boolean isExistProduct = controlIsProductExist(prdProductUpdateRequestDto);

        if (!isExistProduct){
            throw new EntityNotFoundException(PrdProductErrorMessage.PRODUCT_NOT_FOUND);
        }

        PrdProduct prdProduct = PrdProductMapper.INSTANCE.convertToPrdProduct(prdProductUpdateRequestDto);

        PrdProductCategoryType categoryType = prdProductUpdateRequestDto.getCategoryType();

        PrdProductCategory productCategory = getPrdProductCategory(categoryType);

        Long categoryId = productCategory.getId();
        prdProduct.setProductCategoryId(categoryId);

        BigDecimal price = prdProductUpdateRequestDto.getPrice();

        setNewPrices(prdProduct, price, productCategory);

        prdProduct = prdProductEntityService.save(prdProduct);

        PrdProductDto prdProductDto = PrdProductMapper.INSTANCE.convertToPrdProductDto(prdProduct);

        return prdProductDto;
    }

    public void deleteById (Long id) {
        boolean isExistProduct = isExistProduct(id);

        if (isExistProduct){
            prdProductEntityService.deleteById(id);
        }else{
            throw new EntityNotFoundException(PrdProductErrorMessage.PRODUCT_CATEGORY_NOT_FOUND);
        }
    }

    /**
     * It sets new price for the product, and calculate tax prices
     */
    public PrdProductDto updatePrice (Long id, BigDecimal price) {

        boolean isExistProduct = isExistProduct(id);

        if (!isExistProduct){
            throw new EntityNotFoundException(PrdProductErrorMessage.PRODUCT_CATEGORY_NOT_FOUND);
        }

        PrdProduct prdProduct = prdProductEntityService.findById(id);

        Long productCategoryId = prdProduct.getProductCategoryId();
        PrdProductCategory prdProductCategory = prdProductCategoryEntityService.findById(productCategoryId);

        setNewPrices(prdProduct, price, prdProductCategory);

        prdProduct.setPrice(price);
        prdProduct = prdProductEntityService.save(prdProduct);

        PrdProductDto prdProductDto = PrdProductMapper.INSTANCE.convertToPrdProductDto(prdProduct);

        return prdProductDto;
    }

    /**
     * It returns ProductDto list for a specific category
     */
    public List<PrdProductDto> findAllByCategoryType (PrdProductCategoryType prdProductCategoryType) {

        PrdProductCategory categoryType = prdProductCategoryEntityService.findByCategoryType(prdProductCategoryType);

        Long categoryTypeId = categoryType.getId();

        List<PrdProduct> prdProductList = prdProductEntityService.findAllByProductsCategoryId(categoryTypeId);

        List<PrdProductDto> prdProductDtoList = PrdProductMapper.INSTANCE.convertToPrdProductDtoList(prdProductList);

        return prdProductDtoList;
    }

    /**
     * It returns ProductDto list between min and max prices
     */
    public List<PrdProductDto> findAllByTotalPriceBetween (BigDecimal min, BigDecimal max) {

        List<PrdProduct> prdProductList = prdProductEntityService.findAllByTotalPriceBetween(min, max);

        List<PrdProductDto> prdProductDtoList = PrdProductMapper.INSTANCE.convertToPrdProductDtoList(prdProductList);

        return prdProductDtoList;
    }

    /**
     * When price change, it will calculate new prices for the product according to category.
     */
    public void calculatePriceForProduct (PrdProduct prdProduct, PrdProductCategory productCategory) {

        BigDecimal price = prdProduct.getPrice();

        setNewPrices(prdProduct, price, productCategory);

    }

    /**
     * It save products as a list, if exception will be thrown it rollback
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveAll (List<PrdProduct> prdProductList) {

        prdProductEntityService.saveAll(prdProductList);
    }

    private boolean controlIsProductExist (PrdProductUpdateRequestDto prdProductUpdateRequestDto) {

        Long id = prdProductUpdateRequestDto.getId();

        return prdProductEntityService.existById(id);
    }

    private PrdProductCategory getPrdProductCategory (PrdProductCategoryType prdProductCategoryType) {

        return prdProductCategoryEntityService.findByCategoryType(prdProductCategoryType);
    }

    /**
     * It calculates and set new prices for product
     */
    private void setNewPrices (PrdProduct prdProduct, BigDecimal price, PrdProductCategory productCategory) {

        BigDecimal taxPrice = productCategory.getTaxRate().multiply(price).divide(new BigDecimal(100));
        BigDecimal totalPrice = price.add(taxPrice);

        prdProduct.setTaxPrice(taxPrice);
        prdProduct.setTotalPrice(totalPrice);
    }

    private boolean isExistProduct (Long id) {
        boolean isExistProduct = prdProductEntityService.existById(id);
        return isExistProduct;
    }
}
