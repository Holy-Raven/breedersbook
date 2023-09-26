package ru.codesquad.breed.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.codesquad.breed.service.BreedService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin/breeds")
public class AdminBreedController {
    private final BreedService service;

}
