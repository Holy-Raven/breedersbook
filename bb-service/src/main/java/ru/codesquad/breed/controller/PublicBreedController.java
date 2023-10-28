package ru.codesquad.breed.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.breed.dto.BreedShortDto;
import ru.codesquad.breed.service.BreedService;
import ru.codesquad.util.enums.EnumUtil;
import ru.codesquad.util.enums.PetType;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.codesquad.exception.util.ErrorMessages.FROM_ERROR_MESSAGE;
import static ru.codesquad.exception.util.ErrorMessages.SIZE_ERROR_MESSAGE;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/breeds")
@Tag(name = "Public: породы кошек и собак", description = "Публичный API для работы с породами")
public class PublicBreedController {

    private final BreedService breedService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Получение списка пород",
            description = "Получение списка пород(краткая информация) с постраничным выводом. в завистимоти от PetType"
    )
    public List<BreedShortDto> getAllBreedersByPetType(@RequestParam String type,
                                                       @PositiveOrZero(message = FROM_ERROR_MESSAGE)
                                                       @RequestParam(defaultValue = "0") Integer from,
                                                       @Positive(message = SIZE_ERROR_MESSAGE)
                                                       @RequestParam(defaultValue = "10") Integer size)
    {

        PetType petType = EnumUtil.getValue(PetType.class, type);

        log.info("List all Breeders by pet type");
        return breedService.getBreeders(from, size, petType);
    }
}
