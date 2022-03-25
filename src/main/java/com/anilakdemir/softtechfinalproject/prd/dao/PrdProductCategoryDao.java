package com.anilakdemir.softtechfinalproject.prd.dao;

import com.anilakdemir.softtechfinalproject.prd.dto.PrdProductCategoryDetail;
import com.anilakdemir.softtechfinalproject.prd.entity.PrdProductCategory;
import com.anilakdemir.softtechfinalproject.prd.enums.PrdProductCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author anilakdemir
 */
@Repository
public interface PrdProductCategoryDao extends JpaRepository<PrdProductCategory, Long> {

    Optional<PrdProductCategory> findByCategoryType (PrdProductCategoryType prdProductCategoryType);

    boolean existsByCategoryType (PrdProductCategoryType prdProductCategoryType);

    @Query(value = "SELECT " +
            "new com.anilakdemir.softtechfinalproject.prd.dto.PrdProductCategoryDetail(" +
            "prdProductCategory.categoryType," +
            "prdProductCategory.taxRate, " +
            "min(prdProduct.totalPrice), " +
            "max(prdProduct.totalPrice), " +
            "avg(prdProduct.totalPrice), " +
            "count(prdProduct)) " +
            "FROM PrdProductCategory prdProductCategory " +
            "LEFT JOIN PrdProduct prdProduct " +
            "ON prdProductCategory.id = prdProduct.productCategoryId " +
            "GROUP BY prdProductCategory.id")
    List<PrdProductCategoryDetail> findAllProductWithDetail ();
}
