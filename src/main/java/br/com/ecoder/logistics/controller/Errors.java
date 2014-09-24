package br.com.ecoder.logistics.controller;

import java.util.LinkedList;
import java.util.List;

public class Errors {

    private List<Error> errors = new LinkedList<Error>();

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
}
