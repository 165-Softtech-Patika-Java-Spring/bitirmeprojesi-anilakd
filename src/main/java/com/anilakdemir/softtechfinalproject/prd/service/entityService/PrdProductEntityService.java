package com.anilakdemir.softtechfinalproject.prd.service.entityService;

import com.anilakdemir.softtechfinalproject.gen.exceptions.EntityNotFoundException;
import com.anilakdemir.softtechfinalproject.gen.service.entityService.BaseEntityService;
import com.anilakdemir.softtechfinalproject.prd.dao.PrdProductDao;
import com.anilakdemir.softtechfinalproject.prd.entity.PrdProduct;
import com.anilakdemir.softtechfinalproject.prd.exceptions.PrdProductErrorMessage;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author anilakdemir
 */
@Service
public class PrdProductEntityService extends BaseEntityService<PrdProduct, PrdProductDao> {

    public PrdProductEntityService (PrdProductDao dao) {
        super(dao);
    }

    public boolean existById (Long id) {

        return getDao().existsById(id);
    }

    public void deleteById (Long id) {

        getDao().deleteById(id);
    }

    public PrdProduct findById (Long id) {

        return getDao().findById(id).orElseThrow(() -> new EntityNotFoundException(PrdProductErrorMessage.PRODUCT_CATEGORY_NOT_FOUND));
    }

    public List<PrdProduct> findAllByProductsCategoryId (Long productCategoryId) {

        return getDao().findAllByProductCategoryId(productCategoryId);
    }

    public List<PrdProduct> findAllByTotalPriceBetween (BigDecimal min, BigDecimal max) {

        List<PrdProduct> productList = getDao().findAllByTotalPriceBetween(min, max);

        return productList;
    }

    public List<PrdProduct> saveAll (List<PrdProduct> prdProductList) {

        return getDao().saveAll(prdProductList);
    }
}
