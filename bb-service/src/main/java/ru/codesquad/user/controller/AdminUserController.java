package ru.codesquad.user.controller;

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
public class AdminUserController {

    private final UserService userService;

    @DeleteMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {

        log.info("User {} deleted ", userId);
        userService.deleteAdminUser(userId);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<UserDto> getAllUsers(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                     @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        log.info("List all Users");
        return userService.getAllUsers(from, size);
    }
}
