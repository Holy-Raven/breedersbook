package ru.codesquad.userinfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.userinfo.dto.UserInfoDto;
import ru.codesquad.userinfo.dto.UserInfoNewDto;
import ru.codesquad.userinfo.dto.UserInfoUpdateDto;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/userinfo")
public class UserInfoController {

    private final UserInfoService userInfoService;

    @PostMapping("/users/{userId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserInfoDto addUserInfo(@Valid @RequestBody UserInfoNewDto userInfoNewDto,
                                   @PathVariable Long userId) {

        log.info("User id {} add userInfoDto", userId);
        return userInfoService.addUserInfo(userInfoNewDto, userId);
    }

    @PatchMapping("/{userInfo}/users/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserInfoDto updateUserInfo(@Valid @RequestBody UserInfoUpdateDto userInfoUpdateDto,
                                      @PathVariable Long userInfoId,
                                      @PathVariable Long userId) {

        log.info("User id {} update profile {} ", userId, userInfoId);
        return userInfoService.updateUserInfo(userInfoUpdateDto,  userId, userInfoId);
    }
}
