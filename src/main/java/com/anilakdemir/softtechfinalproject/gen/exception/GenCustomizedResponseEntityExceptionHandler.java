package com.anilakdemir.softtechfinalproject.gen.exception;

import com.anilakdemir.softtechfinalproject.gen.dto.RestResponse;
import com.anilakdemir.softtechfinalproject.gen.exceptions.DuplicateException;
import com.anilakdemir.softtechfinalproject.gen.exceptions.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * @author anilakdemir
 */
@ControllerAdvice
@RestController
public class GenCustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid (MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Date errorDate = new Date();
        String message = ex.getFieldError().getDefaultMessage();
        String description = request.getDescription(false);

        GenExceptionResponse genExceptionResponse = new GenExceptionResponse(errorDate, description);

        RestResponse<GenExceptionResponse> restResponse = RestResponse.error(genExceptionResponse);
        restResponse.setMessage(message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResponse);
    }

    @ExceptionHandler
    public ResponseEntity handleEntityNotFoundException (EntityNotFoundException exception, WebRequest webRequest) {

        Date errorDate = new Date();
        String message = exception.getBaseErrorMessage().getMessage();
        String description = webRequest.getDescription(false);

        GenExceptionResponse genExceptionResponse = new GenExceptionResponse(errorDate, description);

        RestResponse<GenExceptionResponse> restResponse = RestResponse.error(genExceptionResponse);
        restResponse.setMessage(message);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restResponse);
    }

    @ExceptionHandler
    public ResponseEntity handleDuplicateException (DuplicateException exception, WebRequest webRequest) {

        Date errorDate = new Date();
        String message = exception.getBaseErrorMessage().getMessage();
        String description = webRequest.getDescription(false);

        GenExceptionResponse genExceptionResponse = new GenExceptionResponse(errorDate, description);

        RestResponse<GenExceptionResponse> restResponse = RestResponse.error(genExceptionResponse);
        restResponse.setMessage(message);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(restResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable (HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Date errorDate = new Date();
        String message = ex.getMessage();
        String description = request.getDescription(false);

        GenExceptionResponse genExceptionResponse = new GenExceptionResponse(errorDate, description);

        RestResponse<GenExceptionResponse> restResponse = RestResponse.error(genExceptionResponse);
        restResponse.setMessage(message);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(restResponse);
    }


}
