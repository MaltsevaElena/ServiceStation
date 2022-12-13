package com.maltseva.servicestation.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Maltseva
 * @version 1.0
 * @since 12.12.2022
 */
@RestController
public abstract class GenericController<T> {
    abstract ResponseEntity<T> getOne(@RequestParam(value = "id") Long id);
}
