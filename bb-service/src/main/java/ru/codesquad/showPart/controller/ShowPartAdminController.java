package ru.codesquad.showPart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.showPart.dto.ShowPartFullDto;
import ru.codesquad.showPart.service.ShowPartService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin/shows")
@Tag(name = "Admin: участие в выставках", description = "API для работы с участием питомцев в выставках")
public class ShowPartAdminController {
    private final ShowPartService service;

    @GetMapping(path = "/{showId}")
    @Operation(summary = "Получение информации об участии в выставке по id",
            description = "Полное описание участия в выставке. Если не найдено, возвращается статус NOT_FOUND."
    )
    ShowPartFullDto getById(@PathVariable @Parameter(description = "Идентификатор участия в выставке") long showId) {
        return service.getById(showId);
    }

    @DeleteMapping(path = "/{showId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление участия в выставке",
            description = "Если не найдено, возвращается статус NOT_FOUND"
    )
    void delete(@PathVariable @Parameter(description = "Идентификатор") long showId) {
        service.delete(showId);
    }
}