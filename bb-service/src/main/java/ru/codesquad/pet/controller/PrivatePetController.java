package ru.codesquad.pet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.pet.dto.PetFullDto;
import ru.codesquad.pet.dto.PetNewDto;
import ru.codesquad.pet.dto.PetUpdateDto;
import ru.codesquad.pet.enums.PetSort;
import ru.codesquad.pet.enums.SaleStatus;
import ru.codesquad.pet.service.PetService;
import ru.codesquad.util.enums.EnumUtil;
import ru.codesquad.util.enums.Gender;

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
@RequestMapping(path = "/private/pets")
@Tag(name = "Private: питомцы", description = "Закрытый API для работы с питомцами")
public class PrivatePetController {
    private final PetService service;

    @GetMapping
    @Operation(summary = "Получение всех питомцев пользователя с возможностью фильтрации",
            description = "Полное описание питомца. Постраничный вывод с сортировкой. Фильтрация по: полу, статуса продажи."
    )
    public List<PetFullDto> getByUserId(@RequestHeader(HEADER_USER) Long userId,
                                        @RequestParam(required = false, name = "gender") String genderParam,
                                        @RequestParam(required = false, name = "saleStatus")
                                        @Parameter(description = "Статус продажи: FOR_SALE, BOOKED, SOLD, NOT_FOR_SALE")
                                        String saleStatusParam,
                                        @Parameter(description = "Сортировка: PRICE_ASC, PRICE_DESC, AGE_ASC, AGE_DESC")
                                        @RequestParam(required = false, name = "sort") String sortParam,
                                        @PositiveOrZero(message = FROM_ERROR_MESSAGE)
                                        @RequestParam(defaultValue = "0") Integer from,
                                        @Positive(message = SIZE_ERROR_MESSAGE)
                                        @RequestParam(defaultValue = "10") Integer size) {
        Gender gender = genderParam == null ? null : EnumUtil.getValue(Gender.class, genderParam);
        SaleStatus saleStatus = saleStatusParam == null ? null : EnumUtil.getValue(SaleStatus.class, saleStatusParam);
        PetSort sort = sortParam == null ? PetSort.DEFAULT : EnumUtil.getValue(PetSort.class, sortParam);
        return service.getAllByUserId(userId, gender, saleStatus, sort, from, size);
    }

    @GetMapping("/{petId}")
    @Operation(summary = "Получение питомца по id",
            description = "Полное описание питомца. Если питомец не найден или пользователь не является владельцем, возвращается сообщение об ошибке."
    )
    public PetFullDto getUsersPetById(@RequestHeader(HEADER_USER) Long userId,
                                      @PathVariable long petId) {
        return service.getUsersPetById(userId, petId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление питомца",
            description = "Добавлять питомцев могут только авторизованные пользователи"
    )
    public PetFullDto add(@RequestHeader(HEADER_USER) Long userId,
                          @Valid @RequestBody PetNewDto petNewDto) {
        return service.add(userId, petNewDto);
    }

    @PatchMapping("/{petId}")
    @Operation(summary = "Обновление питомца по id",
            description = "Если питомец не найден или пользователь не является владельцем, возвращается сообщение об ошибке."
    )
    public PetFullDto update(@RequestHeader(HEADER_USER) Long userId,
                             @PathVariable long petId,
                             @Valid @RequestBody PetUpdateDto petUpdateDto) {
        log.info("Get Pet by id {} for registered User with id {}", petId, userId);
        return service.update(userId, petId, petUpdateDto);
    }

    @DeleteMapping("/{petId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление питомца по id",
            description = "Если питомец не найден или пользователь не является владельцем, возвращается сообщение об ошибке."
    )
    public void delete(@RequestHeader(HEADER_USER) Long userId,
                       @PathVariable long petId) {
        service.deleteByUser(userId, petId);
    }
}