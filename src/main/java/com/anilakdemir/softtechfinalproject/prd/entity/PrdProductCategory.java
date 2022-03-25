package com.anilakdemir.softtechfinalproject.prd.entity;

import com.anilakdemir.softtechfinalproject.gen.entity.BaseEntity;
import com.anilakdemir.softtechfinalproject.prd.enums.PrdProductCategoryType;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author anilakdemir
 */
@Entity
@Table(name = "PRD_PRODUCT_CATEGORY")
@Data
public class PrdProductCategory extends BaseEntity {

    @Id
    @SequenceGenerator(name = "PrdProductCategory", sequenceName = "PRD_PRODUCT_CATEGORY_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "PrdProductCategory", strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY_TYPE", nullable = false, unique = true)
    private PrdProductCategoryType categoryType;

    @Column(name = "TAX_RATE", nullable = false, precision = 19, scale = 2)
    private BigDecimal taxRate;
}