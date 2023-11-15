package ru.codesquad.club.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.club.ClubService;
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
}
