package ru.codesquad.club.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.club.ClubService;
import ru.codesquad.club.dto.ClubDto;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.codesquad.exception.util.ErrorMessages.FROM_ERROR_MESSAGE;
import static ru.codesquad.exception.util.ErrorMessages.SIZE_ERROR_MESSAGE;

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

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Получение списка клубов",
            description = "Получение списка клубов с постраничным выводом."
    )
    public List<ClubDto> getAllClubByAdminFromParam(@RequestParam(required = false, name = "type") String type,
                                                    @RequestParam(required = false, name = "breed") Long breedId,
                                                    @PositiveOrZero(message = FROM_ERROR_MESSAGE)
                                                    @RequestParam(defaultValue = "0") Integer from,
                                                    @Positive(message = SIZE_ERROR_MESSAGE)
                                                    @RequestParam(defaultValue = "10") Integer size)
    {

        log.info("List all Clubs by param for admin");
        return clubService.getAllClubsByAdminFromParam(from, size, type, breedId);
    }
}
