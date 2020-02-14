package com.newbegin.project.newbegin.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtil {
    static Map<String, String> getErrors(BindingResult result) {
        Collector<FieldError, ?, Map<String, String>> fieldErrorMapCollector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
        return result.getFieldErrors()
                .stream()
                .collect(fieldErrorMapCollector);
    }
}
