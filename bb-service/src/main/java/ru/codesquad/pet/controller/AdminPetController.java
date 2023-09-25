package ru.codesquad.pet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.codesquad.pet.service.PetService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin/pets")
public class AdminPetController {
    private final PetService service;
}
