package ru.codesquad.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.user.UserService;
import ru.codesquad.user.dto.UserDto;
import ru.codesquad.user.dto.UserUpdateDto;

import javax.validation.Valid;

import static ru.codesquad.util.Constant.HEADER_USER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/private/users/{userId}")
public class PrivateUserController {

    private final UserService userService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public UserDto getUser(@RequestHeader(HEADER_USER) Long yourId,
                           @PathVariable Long userId) {

        log.info("Get User {} ", userId);
        return userService.getPrivateUserById(userId,yourId);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestHeader(HEADER_USER) Long yourId,
                           @PathVariable Long userId) {

        log.info("User {} deleted ", userId);
        userService.deletePrivateUser(userId, yourId);
    }

    @PatchMapping
    @ResponseStatus(value = HttpStatus.OK)
    public UserDto updateComment(@RequestHeader(HEADER_USER) Long yourId,
                                 @Valid @RequestBody UserUpdateDto userUpdateDto,
                                 @PathVariable Long userId) {

        log.info("User id {} update profile {} ", userId);
        return userService.updateUser(userId, yourId, userUpdateDto);
    }
}
