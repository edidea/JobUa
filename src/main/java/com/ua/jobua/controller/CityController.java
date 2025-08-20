package com.ua.jobua.controller;

import com.ua.jobua.model.City;
import com.ua.jobua.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CityController {
    private final CityRepository cityRepository;

    @GetMapping
    public List<City> list() {
        List<City> all = cityRepository.findAll();
        all.sort(Comparator.comparing(City::getName, String.CASE_INSENSITIVE_ORDER));
        return all;
    }
}
