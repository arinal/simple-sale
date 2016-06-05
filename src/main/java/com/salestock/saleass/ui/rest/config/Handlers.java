package com.salestock.saleass.ui.rest.config;

import org.springframework.http.HttpStatus;
import org.springframework.hateoas.VndErrors;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.salestock.common.rest.NotFoundException;

@ControllerAdvice
class ControllerAdvisor {
    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors NotFoundExceptionHandler(NotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }
}
