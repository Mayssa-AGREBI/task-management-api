package com.example.demotask.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenApiController {

    @GetMapping(value = "/openapi.yaml", produces = "text/yaml")
    public Resource getOpenApi() {
        return new ClassPathResource("openapi.yaml");
    }
}