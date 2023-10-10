package ru.codesquad.userinfo;

import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Private: информация о пользователе", description="Закрытый API для работы с информацией о пользователе")
public class UserInfoController {

    private final UserInfoService userInfoService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserInfoDto addUserInfo(@RequestHeader(HEADER_USER) Long yourId,
                                   @Valid @RequestBody UserInfoNewDto userInfoNewDto) {

        log.info("User id {} add userInfoDto", yourId);
        return userInfoService.addUserInfo(userInfoNewDto, yourId);
    }

    @PatchMapping
    @ResponseStatus(value = HttpStatus.OK)
    public UserInfoDto updateUserInfo(@RequestHeader(HEADER_USER) Long yourId,
                                      @Valid @RequestBody UserInfoUpdateDto userInfoUpdateDto) {

        log.info("User id {} update profile", yourId);
        return userInfoService.updateUserInfo(userInfoUpdateDto,  yourId);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUserInfo(@RequestHeader(HEADER_USER) Long yourId) {

        log.info("User {} deleted his userInfo", yourId);
        userInfoService.deleteUserInfo(yourId);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public UserInfoDto getUserInfo(@RequestHeader(HEADER_USER) Long yourId) {

        log.info("Get UserInfo user {} ", yourId);
        return userInfoService.getUserInfoById(yourId);
    }
}
