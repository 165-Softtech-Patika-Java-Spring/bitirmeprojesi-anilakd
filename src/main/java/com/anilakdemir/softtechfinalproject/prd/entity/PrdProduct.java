package com.anilakdemir.softtechfinalproject.prd.entity;

import com.anilakdemir.softtechfinalproject.gen.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author anilakdemir
 */
@Entity
@Table(name = "PRD_PRODUCT")
@Data
public class PrdProduct extends BaseEntity {

    @Id
    @SequenceGenerator(name = "PrdProduct", sequenceName = "PRD_PRODUCT_ID_SEQ")
    @GeneratedValue(generator = "PrdProduct")
    private Long id;

    @Column(name = "ID_PRODUCT_CATEGORY", nullable = false)
    private Long productCategoryId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PRICE", precision = 19, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "TAX_PRICE", precision = 19, scale = 2)
    private BigDecimal taxPrice;

    @Column(name = "TOTAL_PRICE", precision = 19, scale = 2)
    private BigDecimal totalPrice;
}