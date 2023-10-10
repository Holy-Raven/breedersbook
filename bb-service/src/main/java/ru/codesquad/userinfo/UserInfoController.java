package ru.codesquad.userinfo;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Добавление информации о пользователе",
            description = "Добавлять информацию может только сам пользователь"
    )
    public UserInfoDto addUserInfo(@RequestHeader(HEADER_USER) Long yourId,
                                   @Valid @RequestBody UserInfoNewDto userInfoNewDto) {

        log.info("User id {} add userInfoDto", yourId);
        return userInfoService.addUserInfo(userInfoNewDto, yourId);
    }

    @PatchMapping
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Обновление информации о пользователе",
            description = "Обновлять информацию может только сам пользователь"
    )
    public UserInfoDto updateUserInfo(@RequestHeader(HEADER_USER) Long yourId,
                                      @Valid @RequestBody UserInfoUpdateDto userInfoUpdateDto) {

        log.info("User id {} update profile", yourId);
        return userInfoService.updateUserInfo(userInfoUpdateDto,  yourId);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление информации о пользователе",
            description = "Удалять информацию может только сам пользователь"
    )
    public void deleteUserInfo(@RequestHeader(HEADER_USER) Long yourId) {

        log.info("User {} deleted his userInfo", yourId);
        userInfoService.deleteUserInfo(yourId);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Получение информации о пользователе",
            description = "Получить информацию может только сам пользователь"
    )
    public UserInfoDto getUserInfo(@RequestHeader(HEADER_USER) Long yourId) {

        log.info("Get UserInfo user {} ", yourId);
        return userInfoService.getUserInfoById(yourId);
    }
}
