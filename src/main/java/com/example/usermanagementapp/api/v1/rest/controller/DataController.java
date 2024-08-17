package com.example.usermanagementapp.api.v1.rest.controller;

import com.example.usermanagementapp.api.v1.rest.resources.PowerBiDataSourceDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/data")
public class DataController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Set<String> validTypes = new HashSet<>(Arrays.asList("ALL", "TRAFFIC", "STORE"));


    @GetMapping
    public ResponseEntity<?> getPowerBiData(
            @RequestParam(defaultValue = "ALL") String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        if (!validTypes.contains(type.toUpperCase())) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid type parameter");
            errorResponse.put("message", "Type must be one of: ALL, TRAFFIC, STORE");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            ClassPathResource resource = new ClassPathResource("data.json");
            try (InputStream inputStream = resource.getInputStream()) {
                PowerBiDataSourceDTO powerBiData = objectMapper.readValue(inputStream, PowerBiDataSourceDTO.class);

                // Appliquer les filtres
                powerBiData = filterData(powerBiData, type, startDate, endDate);

                return new ResponseEntity<>(powerBiData, HttpStatus.OK);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private PowerBiDataSourceDTO filterData(PowerBiDataSourceDTO data, String type, LocalDate startDate, LocalDate endDate) {
        if ("TRAFFIC".equalsIgnoreCase(type)) {
            data.setStores(new ArrayList<>());
        } else if ("STORE".equalsIgnoreCase(type)) {
            data.setTraffic(new ArrayList<>());
        }

        if (startDate != null || endDate != null) {
            if (data.getStores() != null) {
                data.setStores(filterByDate(data.getStores(), startDate, endDate));
            }
            if (data.getTraffic() != null) {
                data.setTraffic(filterByDate(data.getTraffic(), startDate, endDate));
            } else {
                data.setTraffic(new ArrayList<>());
            }
        }

        // Ensure that stores and traffic are never null
        if (data.getStores() == null) {
            data.setStores(new ArrayList<>());
        }
        if (data.getTraffic() == null) {
            data.setTraffic(new ArrayList<>());
        }

        return data;
    }

    private List<Map<String, Object>> filterByDate(List<Map<String, Object>> data, LocalDate startDate, LocalDate endDate) {
        return data.stream()
                .filter(entry -> {
                    LocalDate date = LocalDate.parse((String) entry.get("date"));
                    return (startDate == null || !date.isBefore(startDate)) &&
                            (endDate == null || !date.isAfter(endDate));
                })
                .collect(Collectors.toList());
    }
}