package com.anilakdemir.softtechfinalproject.gen.exceptions;

import com.anilakdemir.softtechfinalproject.gen.enums.BaseErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author anilakdemir
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateException extends BusinessException {

    public DuplicateException (BaseErrorMessage baseErrorMessage) {
        super(baseErrorMessage);
    }

}
