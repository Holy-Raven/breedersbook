package ru.codesquad.club.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.club.ClubService;
import ru.codesquad.user.dto.UserShortDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.codesquad.exception.util.ErrorMessages.FROM_ERROR_MESSAGE;
import static ru.codesquad.exception.util.ErrorMessages.SIZE_ERROR_MESSAGE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/clubs")
@Tag(name = "Public: клубы", description = "Публичный API для работы с клубами")
public class PublicClubController {

    private final ClubService clubService;

    @GetMapping("/{clubId}/users")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Получение списка подписчиков клуба",
            description = "Получение списка подписчиков(краткая информация) с постраничным выводом."
    )
    public List<UserShortDto> getAllSubscribersClubById(@PathVariable Long clubId,
                                                              @PositiveOrZero(message = FROM_ERROR_MESSAGE)
                                                              @RequestParam(defaultValue = "0") Integer from,
                                                              @Positive(message = SIZE_ERROR_MESSAGE)
                                                              @RequestParam(defaultValue = "10") Integer size)
    {
        log.info("Public List all subscribers club id{}", clubId);
        return clubService.getAllSubscribersClubById(from, size, clubId);
    }
}
