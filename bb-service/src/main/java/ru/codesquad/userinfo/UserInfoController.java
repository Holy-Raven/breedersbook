package ru.codesquad.userinfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.userinfo.dto.UserInfoDto;
import ru.codesquad.userinfo.dto.UserInfoNewDto;
import ru.codesquad.userinfo.dto.UserInfoUpdateDto;

import javax.validation.Valid;

import static ru.codesquad.util.Constant.HEADER_USER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/userinfo")
public class UserInfoController {

    private final UserInfoService userInfoService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserInfoDto addUserInfo(@RequestHeader(HEADER_USER) Long yourId,
                                   @Valid @RequestBody UserInfoNewDto userInfoNewDto) {

        log.info("User id {} add userInfoDto", yourId);
        return userInfoService.addUserInfo(userInfoNewDto, yourId);
    }

    @PatchMapping("/{userInfoId}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserInfoDto updateUserInfo(@RequestHeader(HEADER_USER) Long yourId,
                                      @Valid @RequestBody UserInfoUpdateDto userInfoUpdateDto,
                                      @PathVariable Long userInfoId) {

        log.info("User id {} update profile {} ", yourId, userInfoId);
        return userInfoService.updateUserInfo(userInfoUpdateDto,  yourId, userInfoId);
    }
}
