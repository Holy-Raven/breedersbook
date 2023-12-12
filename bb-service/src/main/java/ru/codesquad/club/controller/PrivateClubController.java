package ru.codesquad.club.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.club.ClubService;
import ru.codesquad.club.clubsusers.dto.ClubsUsersShortDto;
import ru.codesquad.club.dto.ClubDto;
import ru.codesquad.club.dto.ClubNewDto;

import javax.validation.Valid;

import static ru.codesquad.util.Constant.HEADER_USER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/private/clubs")
@Tag(name = "Private: клубы", description = "Закрытый API для работы с клубами")
public class PrivateClubController {

    private final ClubService clubService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "Добавление клуба",
            description = "Добавлять клубы могут только авторизованные пользователи"
    )
    public ClubDto addClub(@RequestHeader(HEADER_USER) Long yourId,
                           @Valid @RequestBody ClubNewDto ClubNewDto) {

        log.info("User {} add club {} ", yourId, ClubNewDto.getName());
        return clubService.addClub(yourId, ClubNewDto);
    }

    @PostMapping("/{clubId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "Вступление в клуба",
            description = "Вступить в клубы могут только авторизованные пользователи"
    )
    public ClubsUsersShortDto joinInClub(@RequestHeader(HEADER_USER) Long yourId,
                                         @PathVariable Long clubId) {

        log.info("User {} join in club {} ", yourId, clubId);
        return clubService.joinInClub(yourId, clubId);
    }

    @DeleteMapping("/{clubId}/user/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление участника из своего клуба по id",
            description = "Удалить юзера из клюуба может только участник клуба с ролью ADMIN"
    )
    public void kickOutOfClub(@RequestHeader(HEADER_USER) Long yourId,
                                           @PathVariable Long clubId,
                                           @PathVariable Long userId) {

        log.info("Admin {} kicked user {} from club {}", yourId, userId, clubId);
        clubService.kickOutOfClub(clubId, yourId, userId);
    }

    @DeleteMapping("/{clubId}/leave")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Выйти из состава клуба по id",
            description = "Выйти можно из клуба только участником которого ты являешься"
    )
    public void exitFromClub(@RequestHeader(HEADER_USER) Long yourId,
                                          @PathVariable Long clubId) {

        log.info(" User {} exit from club {}", yourId, clubId);
        clubService.exitOutOfClub(clubId, yourId);
    }

    @GetMapping("/{clubId}")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Получение клуба по id",
            description = "Полное описание клуба. Если клуб не найден или пользователь не является владельцем, возвращается сообщение об ошибке."
    )
    public ClubDto getPrivateClubById(@RequestHeader(HEADER_USER) Long yourId,
                                      @PathVariable Long clubId) {

        log.info("Get Club {} ", clubId);
        return clubService.getPrivateClubById(clubId, yourId);
    }

    @DeleteMapping("/{clubId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление клуба по id",
            description = "Если клуб не найден или пользователь не является владельцем, возвращается сообщение об ошибке."
    )
    public void deleteClubById(@RequestHeader(HEADER_USER) Long yourId,
                               @PathVariable Long clubId) {

        log.info("User {} deleted club {}", yourId, clubId);
        clubService.deletePrivateClub(yourId, clubId);
    }
}
