package ru.codesquad.kennel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.kennel.KennelService;
import ru.codesquad.kennel.dto.KennelShortDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

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
    public List<KennelShortDto> getAllKennels(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                              @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        log.info("List all Kennels");
        return kennelService.getPublicAllKennels(from, size);
    }
}