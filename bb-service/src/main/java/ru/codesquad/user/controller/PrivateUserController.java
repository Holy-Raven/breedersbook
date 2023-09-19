package ru.codesquad.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.user.UserService;
import ru.codesquad.user.dto.UserDto;
import ru.codesquad.user.dto.UserUpdateDto;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/private/users")
public class PrivateUserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserDto getUser(@PathVariable Long userId) {

        log.info("Get User {} ", userId);
        return userService.getPrivateUserById(userId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {

        log.info("User {} deleted ", userId);
        userService.deletePrivateUser(userId);
    }

    @PatchMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserDto updateComment(@Valid @RequestBody UserUpdateDto userUpdateDto,
                                        @PathVariable Long userId) {

        log.info("User id {} update profile {} ", userId);
        return userService.updateUser(userId, userUpdateDto);
    }
}
