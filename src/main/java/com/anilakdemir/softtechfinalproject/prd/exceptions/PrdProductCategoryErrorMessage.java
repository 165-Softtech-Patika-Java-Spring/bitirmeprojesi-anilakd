package com.anilakdemir.softtechfinalproject.prd.exceptions;

import com.anilakdemir.softtechfinalproject.gen.enums.BaseErrorMessage;

/**
 * @author anilakdemir
 */
public enum PrdProductCategoryErrorMessage implements BaseErrorMessage {

    PRODUCT_CATEGORY_NOT_FOUND("Product category not found"),
    PRODUCT_CATEGORY_IS_ALREADY_EXIST("Product category is already exist"),
    ;


    private String messsage;

    PrdProductCategoryErrorMessage (String messsage) {
        this.messsage = messsage;
    }

    @Override
    public String getMessage () {
        return messsage;
    }
}
