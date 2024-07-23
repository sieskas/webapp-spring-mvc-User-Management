package com.example.usermanagementapp.api.v1.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/data")
public class DataController {

    @GetMapping(produces = "application/json")
    public Map<String, Object> getData() throws IOException {
        // Lire le fichier JSON
        ClassPathResource resource = new ClassPathResource("data.json");
        return new ObjectMapper().readValue(resource.getInputStream(), Map.class);
    }
}