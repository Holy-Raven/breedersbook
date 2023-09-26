package ru.codesquad.pet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.pet.service.PetService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin/pets")
public class AdminPetController {
    private final PetService service;

    @DeleteMapping("/{petId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long petId) {
        service.delete(petId);
    }
}
