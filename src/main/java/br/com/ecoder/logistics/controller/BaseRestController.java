package br.com.ecoder.logistics.controller;

import java.util.LinkedList;
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

    protected List<String> buildErrorResource(List<ObjectError> errorList) {

        LinkedList<String> errors = new LinkedList<String>();

        for (ObjectError objError : errorList) {
            String message = objError.getDefaultMessage();
            errors.add(message);
        }

        return errors;
    }

}
