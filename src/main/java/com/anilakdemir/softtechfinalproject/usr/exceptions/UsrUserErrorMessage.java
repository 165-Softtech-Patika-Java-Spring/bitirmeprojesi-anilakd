package com.anilakdemir.softtechfinalproject.usr.exceptions;

import com.anilakdemir.softtechfinalproject.gen.enums.BaseErrorMessage;

/**
 * @author anilakdemir
 */
public enum UsrUserErrorMessage implements BaseErrorMessage {
    USER_NOT_FOUND("User not found"),
    USERNAME_ALREADY_EXIST("Username is already exist"),
    ;

    private String message;

    UsrUserErrorMessage (String message) {
        this.message = message;
    }

    @Override
    public String getMessage () {
        return message;
    }
}
