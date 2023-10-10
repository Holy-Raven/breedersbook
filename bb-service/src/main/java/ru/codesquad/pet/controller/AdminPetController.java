package ru.codesquad.pet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.pet.service.PetService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin/pets")
@Tag(name="Admin: питомцы", description="API для работы с питомцами")
public class AdminPetController {
    private final PetService service;

    @DeleteMapping("/{petId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление питомца по id",
            description = "Если питомец не найден, возвращается статус NOT_FOUND и сообщение об ошибке."
    )
    public void delete(@PathVariable @Parameter(description = "Идентификатор питомца") long petId) {
        service.deleteByAdmin(petId);
    }
}
