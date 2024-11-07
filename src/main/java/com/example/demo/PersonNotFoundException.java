package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends RuntimeException  {
    public PersonNotFoundException(Long id) {
        super("Could not find peson with id: " + id);
    }
}