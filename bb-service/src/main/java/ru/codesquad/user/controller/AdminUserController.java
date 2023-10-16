package ru.codesquad.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.user.UserService;
import ru.codesquad.user.dto.UserDto;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
@Tag(name="Admin: пользователи", description="API для работы с пользователями")
public class AdminUserController {

    private final UserService userService;

    @DeleteMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление пользователя по id",
            description = "Если пользователь не найден, возвращается статус NOT_FOUND и сообщение об ошибке."
    )
    public boolean deleteUserByAdmin(@PathVariable Long userId,
                                  @RequestParam (name = "delete") String typeDelete) {

        log.info("Admin deleted user {}", userId);
       return userService.deleteUserByAdmin(userId, typeDelete);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Получение списка пользователей",
            description = "Получение списка пользователей с постраничным выводом."
    )
    public List<UserDto> getAllUsers(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                     @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        log.info("List all Users");
        return userService.getAllUsers(from, size);
    }
}
