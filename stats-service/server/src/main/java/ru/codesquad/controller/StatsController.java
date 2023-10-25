package ru.codesquad.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.dto.HitDto;
import ru.codesquad.dto.StatsDto;
import ru.codesquad.service.StatsService;
import ru.codesquad.util.Constant;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "Статистика", description = "API для работы со статистикой")
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Сохранение перехода по эндпоинту",
            description = "Позволяет сохранить просмотр сущности"
    )
    public void saveHit(@RequestBody @Valid HitDto hitDto,
                        @RequestParam @Parameter(description = "Наименование сущности") String entity) {
        log.info("Creating hit");
        statsService.save(hitDto, entity);
    }

    @GetMapping("/stats")
    @Operation(
            summary = "Получение статистики",
            description = "Позволяет получить статистику просмотров сущности(ей) при указании конкретных эндроинтов " +
                    "либо статистику по всем сущностям заданной категории в порядке убывания просмотров"
    )
    public List<StatsDto> getStats(@RequestParam @Parameter(description = "Наименование сущности") String entity,
                                   @RequestParam @DateTimeFormat(pattern = Constant.DATE_TIME_FORMAT)
                                   @Parameter(description = "Дата начала периода просмотров") LocalDateTime start,
                                   @RequestParam @DateTimeFormat(pattern = Constant.DATE_TIME_FORMAT)
                                   @Parameter(description = "Дата окончания периода просмотров") LocalDateTime end,
                                   @RequestParam(required = false)
                                   @Parameter(description = "Эндпоинты питомцев") String[] uris,
                                   @RequestParam(defaultValue = "false")
                                   @Parameter(description = "Уникальность ip-адреса") boolean unique) {
        log.info("Getting stats");
        if (uris == null) {
            return statsService.getStatsWithoutUris(entity, start, end, unique);
        }
        return statsService.getStatsWithUris(entity, start, end, uris, unique);
    }
}
