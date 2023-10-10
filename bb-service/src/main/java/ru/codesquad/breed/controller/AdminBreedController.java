package ru.codesquad.breed.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.breed.dto.BreedFullDto;
import ru.codesquad.breed.dto.BreedNewDto;
import ru.codesquad.breed.service.BreedService;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin/breeds")
@Tag(name = "Admin: породы кошек и собак", description = "API для работы с породами")
public class AdminBreedController {
    private final BreedService service;

    @GetMapping(path = "/{breedId}")
    @Operation(summary = "Получение породы по id",
            description = "Полное описание породы. Если порода не найдена, возвращается статус NOT_FOUND и сообщение об ошибке."
    )
    BreedFullDto getById(@PathVariable @Parameter(description = "Идентификатор породы") long breedId) {
        return service.getById(breedId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление породы",
            description = "Если порода существует, возвращается статус CONFLICT"
    )
    BreedFullDto add(@RequestBody @Valid BreedNewDto breedNewDto) {
        return service.add(breedNewDto);
    }
}
