package ru.codesquad.breed.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.codesquad.breed.service.BreedService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/breeds")
@Tag(name = "Public: породы кошек и собак", description = "Публичный API для работы с породами")
public class PublicBreedController {
    private final BreedService service;
}
