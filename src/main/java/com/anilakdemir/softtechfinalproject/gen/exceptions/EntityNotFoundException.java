package com.anilakdemir.softtechfinalproject.gen.exceptions;

import com.anilakdemir.softtechfinalproject.gen.enums.BaseErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author anilakdemir
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException (BaseErrorMessage baseErrorMessage) {
        super(baseErrorMessage);
    }
}