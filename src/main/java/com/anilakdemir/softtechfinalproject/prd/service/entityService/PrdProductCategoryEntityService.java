package com.anilakdemir.softtechfinalproject.prd.service.entityService;

import com.anilakdemir.softtechfinalproject.gen.exceptions.EntityNotFoundException;
import com.anilakdemir.softtechfinalproject.gen.service.entityService.BaseEntityService;
import com.anilakdemir.softtechfinalproject.prd.dao.PrdProductCategoryDao;
import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductCategoryDetail;
import com.anilakdemir.softtechfinalproject.prd.entity.PrdProductCategory;
import com.anilakdemir.softtechfinalproject.prd.enums.PrdProductCategoryType;
import com.anilakdemir.softtechfinalproject.prd.exceptions.PrdProductErrorMessage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author anilakdemir
 */
@Service
public class PrdProductCategoryEntityService extends BaseEntityService<PrdProductCategory, PrdProductCategoryDao> {

    public PrdProductCategoryEntityService (PrdProductCategoryDao dao) {
        super(dao);
    }

    public PrdProductCategory findByCategoryType (PrdProductCategoryType prdProductCategoryType) {
        return getDao().findByCategoryType(prdProductCategoryType).orElseThrow(() -> new EntityNotFoundException(PrdProductErrorMessage.PRODUCT_CATEGORY_NOT_FOUND));
    }

    public boolean existByCategoryType (PrdProductCategoryType prdProductCategoryType) {
        return getDao().existsByCategoryType(prdProductCategoryType);
    }

    public PrdProductCategory findById (Long productCategoryId) {
        return getDao().findById(productCategoryId).orElseThrow(() -> new EntityNotFoundException(PrdProductErrorMessage.PRODUCT_CATEGORY_NOT_FOUND));
    }

    public List<PrdProductCategoryDetail> findAllProductWithDetail () {

        return getDao().findAllProductWithDetail();
    }
}
