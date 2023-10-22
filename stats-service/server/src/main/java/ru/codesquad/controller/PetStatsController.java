package ru.codesquad.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.dto.HitDto;
import ru.codesquad.dto.StatsDto;
import ru.codesquad.service.PetStatsService;
import ru.codesquad.util.Constant;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/pets")
@AllArgsConstructor
@Slf4j
@Tag(name = "Статистика питомцев", description = "API для работы со статистикой питомцев")
public class PetStatsController {
    private final PetStatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Сохранение перехода по эндпоинту",
            description = "Позволяет сохранить просмотр питомца"
    )
    public void saveHit(@RequestBody @Valid HitDto hitDto) {
        log.info("Creating hit");
        statsService.save(hitDto);
    }

    @GetMapping("/stats")
    @Operation(
            summary = "Получение статистики",
            description = "Позволяет получить статистику просмотров питомца(ев) при указании конкретных эндроинтов " +
                    "либо статистику по всем питомцам в порядке убывания просмотров"
    )
    public List<StatsDto> getStats(@RequestParam @DateTimeFormat(pattern = Constant.DATE_TIME_FORMAT)
                                   LocalDateTime start,
                                   @RequestParam @DateTimeFormat(pattern = Constant.DATE_TIME_FORMAT)
                                   LocalDateTime end,
                                   @RequestParam(required = false) String[] uris,
                                   @RequestParam(defaultValue = "false") boolean unique) {
        log.info("Getting stats");
        if (uris == null) {
            return statsService.getStatsWithoutUris(start, end, unique);
        }
        return statsService.getStatsWithUris(start, end, uris, unique);
    }
}
