package ru.codesquad.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.user.UserService;
import ru.codesquad.user.dto.UserNewDto;
import ru.codesquad.user.dto.UserDto;
import ru.codesquad.user.dto.UserShortDto;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
@Tag(name="Public: пользователи", description="Публичный API для работы с пользователями")
public class PublicUserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "Регистрация пользователя",
            description = "Публичный метод регистрации нового пользователя"
    )
    public UserDto addUser(@Valid @RequestBody UserNewDto newUserDto) {

        log.info("Add User {} ", newUserDto.getName());
        return userService.addUser(newUserDto);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Получение пользователя по id",
            description = "Краткая информация о пользователе"
    )
    @ResponseStatus(value = HttpStatus.OK)
    public UserShortDto getUser(@PathVariable Long userId) {

        log.info("Get User {} ", userId);
        return userService.getPublicUserById(userId);
    }
}
