package ru.codesquad.kennel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.kennel.KennelService;
import ru.codesquad.kennel.dto.KennelShortDto;
import ru.codesquad.util.enums.EnumUtil;
import ru.codesquad.util.enums.PetType;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.codesquad.exception.util.ErrorMessages.FROM_ERROR_MESSAGE;
import static ru.codesquad.exception.util.ErrorMessages.SIZE_ERROR_MESSAGE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/kennels")
@Tag(name = "Public: питомники", description = "Публичный API для работы с питомниками")
public class PublicKennelController {

    private final KennelService kennelService;

    @GetMapping("/{kennelId}")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Получение питомника по id",
            description = "Короткое описание питомника. Если питомник не найден, возвращается сообщение об ошибке."
    )
    public KennelShortDto getKennel(@PathVariable Long kennelId) {

        log.info("Get kennel {} ", kennelId);
        return kennelService.getPublicKennelById(kennelId);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Получение списка питомников",
            description = "Получение списка питомников(краткая информация) с постраничным выводом."
    )
    public List<KennelShortDto> getAllKennelByPublicFromParam(@RequestParam(required = false, name = "type") String type,
                                                            @RequestParam(required = false, name = "breed") Long breed,
                                                            @PositiveOrZero(message = FROM_ERROR_MESSAGE)
                                                            @RequestParam(defaultValue = "0") Integer from,
                                                            @Positive(message = SIZE_ERROR_MESSAGE)
                                                            @RequestParam(defaultValue = "10") Integer size)
    {
        log.info("List all Kennels by param for public");
        return kennelService.getAllKennelByPublicFromParam(from, size, type, breed);
    }
}