package br.com.ecoder.logistics.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseBody
public class BaseRestController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleExceptionInternal(ex, buildErrorResource(ex.getBindingResult().getAllErrors()), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleExceptionInternal(ex, buildErrorResource(ex.getAllErrors()), headers, status, request);
    }

    protected Errors buildErrorResource(List<ObjectError> errorList) {

        Errors errors = new Errors();

        for (ObjectError objError : errorList) {

            String message = objError.getDefaultMessage();

            if (message != null && !message.trim().isEmpty()) {
                errors.getErrors().add(new Error(message));
            }
        }

        return errors;
    }

}
