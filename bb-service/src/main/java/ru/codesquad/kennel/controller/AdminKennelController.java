package ru.codesquad.kennel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.kennel.KennelService;
import ru.codesquad.kennel.dto.KennelDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/kennels")
@Tag(name = "Admin: питомники", description = "API для работы с питомниками")
public class AdminKennelController {

    private final KennelService kennelService;

    @DeleteMapping("/{kennelId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление питомника по id",
            description = "Если питомник не найден, возвращается статус NOT_FOUND и сообщение об ошибке."
    )
    public void deleteKennel(@PathVariable Long kennelId) {

        log.info("Admin deleted kennel {} ", kennelId);
        kennelService.deleteKennel(kennelId);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Получение списка питомников",
            description = "Получение списка питомников с постраничным выводом."
    )
    public List<KennelDto> getAllKennels(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                         @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        log.info("List all Users");
        return kennelService.getAdminAllKennels(from, size);
    }
}
