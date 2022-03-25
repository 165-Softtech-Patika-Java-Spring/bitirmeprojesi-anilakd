package com.anilakdemir.softtechfinalproject.gen.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author anilakdemir
 */
@Data
@AllArgsConstructor
public class GenExceptionResponse {

    private Date errorDate;
    private String detail;
}
