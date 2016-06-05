package com.salestock.common.rest;

public class NotFoundException extends RuntimeException {
    public NotFoundException(int id) {
        this(Integer.valueOf(id).toString());
    }

    public NotFoundException(String id) {
        super("could not find entity with id \'" + id + "\'");
    }
}
