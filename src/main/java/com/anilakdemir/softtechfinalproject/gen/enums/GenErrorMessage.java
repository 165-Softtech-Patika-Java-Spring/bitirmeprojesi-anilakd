package com.anilakdemir.softtechfinalproject.gen.enums;

/**
 * @author anilakdemir
 */
public enum GenErrorMessage implements BaseErrorMessage {

    ITEM_NOT_FOUND("Item not found! -> %s"),
    ;

    private String message;

    GenErrorMessage (String message) {
        this.message = message;
    }

    public String getMessage () {
        return message;
    }

    @Override
    public String toString () {
        return message;
    }
}
