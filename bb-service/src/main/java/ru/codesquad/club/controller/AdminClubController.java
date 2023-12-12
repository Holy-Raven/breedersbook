package ru.codesquad.club.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.club.ClubService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/clubs")
@Tag(name = "Admin: клубы", description = "API для работы с клубами")
public class AdminClubController {

    private final ClubService clubService;

    @DeleteMapping("/{clubId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление клуба по id",
            description = "Если клуб не найден, возвращается статус NOT_FOUND и сообщение об ошибке."
    )
    public void deleteClub(@PathVariable Long clubId) {

        log.info("Admin deleted club {} ", clubId);
        clubService.deleteClub(clubId);
    }
}
