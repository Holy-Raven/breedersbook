package ru.codesquad.kennel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.kennel.KennelService;
import ru.codesquad.kennel.dto.KennelDto;
import ru.codesquad.kennel.dto.KennelNewDto;
import ru.codesquad.kennel.dto.KennelUpdateDto;
import javax.validation.Valid;

import static ru.codesquad.util.Constant.HEADER_USER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/private/kennels")
@Tag(name="Private: питомники", description="Закрытый API для работы с питомниками")
public class PrivateKennelController {

    private final KennelService kennelService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "Добавление питомника",
            description = "Добавлять питомники могут только авторизованные пользователи"
    )
    public KennelDto addKennel(@RequestHeader(HEADER_USER) Long yourId,
                               @Valid @RequestBody KennelNewDto kennelNewDto) {

        log.info("User {} add kennel {} ", yourId, kennelNewDto.getName());
        return kennelService.addKennel(yourId, kennelNewDto);
    }

    @GetMapping("/{kennelId}")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Получение питомника по id",
            description = "Полное описание питомника. Если питомник не найден или пользователь не является владельцем, возвращается сообщение об ошибке."
    )
    public KennelDto getKennel(@RequestHeader(HEADER_USER) Long yourId,
                               @PathVariable Long kennelId) {

        log.info("Get Kennel {} ", kennelId);
        return kennelService.getPrivateKennelById(kennelId,yourId);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление питомника по id",
            description = "Если питомник не найден или пользователь не является владельцем, возвращается сообщение об ошибке."
    )
    public void deleteKennel(@RequestHeader(HEADER_USER) Long yourId) {

        log.info("User {} deleted his kennel ", yourId);
        kennelService.deletePrivateKennel(yourId);
    }

    @PatchMapping
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Обновление питомника по id",
            description = " Если питомник не найден или пользователь не является владельцем, возвращается сообщение об ошибке."
    )
    public KennelDto updateKennel(@RequestHeader(HEADER_USER) Long yourId,
                                 @Valid @RequestBody KennelUpdateDto kennelUpdateDto) {

        log.info("User id {} update profile his kennel", yourId);
        return kennelService.updateKennel(yourId, kennelUpdateDto);
    }
}
