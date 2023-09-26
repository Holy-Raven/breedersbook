package ru.codesquad.pet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.pet.dto.NewPetDto;
import ru.codesquad.pet.dto.PetFullDto;
import ru.codesquad.pet.dto.UpdatePetDto;
import ru.codesquad.pet.service.PetService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.codesquad.exception.util.ErrorMessages.FROM_ERROR_MESSAGE;
import static ru.codesquad.exception.util.ErrorMessages.SIZE_ERROR_MESSAGE;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/pets")
public class PrivatePetController {
    private final PetService service;

    @GetMapping
    public List<PetFullDto> getByUserId(@PathVariable long userId,
                                        @PositiveOrZero(message = FROM_ERROR_MESSAGE)
                                        @RequestParam(defaultValue = "0") Integer from,
                                        @Positive(message = SIZE_ERROR_MESSAGE)
                                        @RequestParam(defaultValue = "10") Integer size) {
        return service.getAllByUserId(userId, from, size);
    }

    @GetMapping("/{petId}")
    public PetFullDto getUsersPetById(@PathVariable long userId,
                                      @PathVariable long petId) {
        return service.getUsersPetById(userId, petId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetFullDto add(@PathVariable long userId,
                          @Valid @RequestBody NewPetDto newPetDto) {
        return service.add(userId, newPetDto);
    }

    @PatchMapping("/{petId}")
    public PetFullDto update(@PathVariable long userId,
                             @PathVariable long petId,
                             @Valid @RequestBody UpdatePetDto updatePetDto) {

        return service.update(userId, petId, updatePetDto);
    }

    @DeleteMapping("/{petId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long userId,
                       @PathVariable long petId) {
        service.delete(userId, petId);
    }
}