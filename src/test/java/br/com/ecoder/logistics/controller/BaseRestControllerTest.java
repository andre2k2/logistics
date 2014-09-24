package br.com.ecoder.logistics.controller;

import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class BaseRestControllerTest {

    @Mock
    MethodParameter parameter;

    @Mock
    BindingResult bindingResult;

    List<ObjectError> errorList;

    @Before
    public void init() {

        MockitoAnnotations.initMocks(this);

        errorList = new LinkedList<ObjectError>();
        errorList.add(new ObjectError("teste1", "teste1"));
        errorList.add(new ObjectError("teste2", "teste2"));

        when(bindingResult.getAllErrors()).thenReturn(errorList);
    }

    @Test
    public void should_return_errors_list_calling_handleMethodArgumentNotValid() {

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(parameter, bindingResult);

        BaseRestController baseRestController = new BaseRestController();
        ResponseEntity<Object> response = baseRestController.handleMethodArgumentNotValid(exception, null, null, null);

        Assert.assertNotNull(response);

        Object body = response.getBody();

        Assert.assertNotNull(body);
        Assert.assertTrue(body instanceof Errors);

        Errors errors = (Errors) body;

        Assert.assertNotNull(errors.getErrors());
        Assert.assertEquals(errors.getErrors().size(), 2);
        Assert.assertEquals(errors.getErrors().get(0).getMessage(), "teste1");
        Assert.assertEquals(errors.getErrors().get(1).getMessage(), "teste2");
    }

    @Test
    public void should_return_errors_list_calling_handleBindException() {

        BindException exception = new BindException(bindingResult);

        BaseRestController baseRestController = new BaseRestController();
        ResponseEntity<Object> response = baseRestController.handleBindException(exception, null, null, null);

        Assert.assertNotNull(response);

        Object body = response.getBody();

        Assert.assertNotNull(body);
        Assert.assertTrue(body instanceof Errors);

        Errors errors = (Errors) body;

        Assert.assertNotNull(errors.getErrors());
        Assert.assertEquals(errors.getErrors().size(), 2);
        Assert.assertEquals(errors.getErrors().get(0).getMessage(), "teste1");
        Assert.assertEquals(errors.getErrors().get(1).getMessage(), "teste2");
    }
}
