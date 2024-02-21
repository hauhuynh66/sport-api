package com.server.controller.advice;

import java.io.IOException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.server.exception.NoRecordException;
import com.server.exception.QueryParamException;

@RestController
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(IOException.class)
    public String handleIOException(IOException exception) {
        return "{message : '"+ exception.getMessage() +"'}";
    }

    @ExceptionHandler(QueryParamException.class)
    public String handleQueryParamException(QueryParamException exception) {
        return "{message : '"+ exception.getMessage() +"'}";
    }

    @ExceptionHandler(NoRecordException.class)
    public String handleNoRecordException(NoRecordException exception) {
        return "{message : '"+ exception.getMessage() +"'}";
    }
}
