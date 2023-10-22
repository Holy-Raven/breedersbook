package ru.codesquad.showPart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.showPart.dto.ShowPartFullDto;
import ru.codesquad.showPart.dto.ShowPartNewDto;
import ru.codesquad.showPart.dto.ShowPartUpdateDto;
import ru.codesquad.showPart.service.ShowPartService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.codesquad.exception.util.ErrorMessages.FROM_ERROR_MESSAGE;
import static ru.codesquad.exception.util.ErrorMessages.SIZE_ERROR_MESSAGE;
import static ru.codesquad.util.Constant.HEADER_USER;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/private/shows")
@Tag(name = "Private: участие в выставках", description = "Приватный API для работы с участием в выставках")
public class ShowPartPrivateController {
    private final ShowPartService service;

    @GetMapping(path = "/pets/{petId}")
    @Operation(summary = "Получение списка участия в выставках по id питомца с постраничным выводом",
            description = "Полное описание участия в выставке."
    )
    List<ShowPartFullDto> getByPetId(@RequestHeader(HEADER_USER) Long userId,
                                     @PathVariable @Parameter(description = "Идентификатор питомца") long petId,
                                     @Parameter(description = "Сортировка по дате: по умолчанию от новых к старым")
                                     @RequestParam(defaultValue = "false") Boolean asc,
                                     @PositiveOrZero(message = FROM_ERROR_MESSAGE)
                                     @RequestParam(defaultValue = "0") Integer from,
                                     @Positive(message = SIZE_ERROR_MESSAGE)
                                     @RequestParam(defaultValue = "10") Integer size) {
        return service.getByPetId(userId, petId, asc, from, size);
    }

    @GetMapping(path = "/{showId}")
    @Operation(summary = "Получение информации об участии в выставке по id",
            description = "Полное описание участия в выставке. Если не найдено, возвращается статус NOT_FOUND."
    )
    ShowPartFullDto getById(@RequestHeader(HEADER_USER) Long userId,
                            @PathVariable @Parameter(description = "Идентификатор участия в выставке") long showId) {
        return service.getByUserById(userId, showId);
    }

    @PostMapping("/pets/{petId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление участия в выставке")
    ShowPartFullDto add(@RequestHeader(HEADER_USER) Long userId,
                        @PathVariable Long petId,
                        @RequestBody @Valid ShowPartNewDto showPartNewDto) {
        return service.add(userId, petId, showPartNewDto);
    }

    @PatchMapping("/{showId}")
    @Operation(summary = "Обновление участия в выставке")
    ShowPartFullDto update(@RequestHeader(HEADER_USER) Long userId,
                           @PathVariable Long showId,
                           @RequestBody @Valid ShowPartUpdateDto showPartUpdateDto) {
        return service.update(userId, showId, showPartUpdateDto);
    }

    @DeleteMapping(path = "/{showId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление участия в выставке",
            description = "Если не найдено, возвращается статус NOT_FOUND"
    )
    void delete(@RequestHeader(HEADER_USER) Long userId,
                @PathVariable @Parameter(description = "Идентификатор") long showId) {
        service.deleteByUser(userId, showId);
    }
}
