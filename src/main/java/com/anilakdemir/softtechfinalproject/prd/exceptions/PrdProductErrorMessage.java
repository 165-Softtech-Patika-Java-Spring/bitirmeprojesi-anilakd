package com.anilakdemir.softtechfinalproject.prd.exceptions;

import com.anilakdemir.softtechfinalproject.gen.enums.BaseErrorMessage;

/**
 * @author anilakdemir
 */
public enum PrdProductErrorMessage implements BaseErrorMessage {

    PRODUCT_CATEGORY_NOT_FOUND("Product Category not found"),
    PRODUCT_NOT_FOUND("Product not found");

    private String message;

    PrdProductErrorMessage (String message) {
        this.message = message;
    }

    @Override
    public String getMessage () {
        return message;
    }
}
