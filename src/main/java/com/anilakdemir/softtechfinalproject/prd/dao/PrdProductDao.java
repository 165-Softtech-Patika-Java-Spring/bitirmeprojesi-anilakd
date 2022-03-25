package com.anilakdemir.softtechfinalproject.prd.dao;

import com.anilakdemir.softtechfinalproject.prd.entity.PrdProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author anilakdemir
 */
@Repository
public interface PrdProductDao extends JpaRepository<PrdProduct, Long> {

    List<PrdProduct> findAllByProductCategoryId (Long productCategoryId);

    List<PrdProduct> findAllByTotalPriceBetween (BigDecimal min, BigDecimal max);
}
