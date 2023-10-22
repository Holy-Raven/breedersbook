package ru.codesquad.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Статистика просмотров")
public class StatsDto {
    @Schema(description = "Наименование сервиса, передавшего просмотр", example = "bb-service")
    private String app;
    @Schema(description = "Эндпоинт сущности", example = "/pets/1")
    private String uri;
    @Schema(description = "Количество просмотров")
    private long hits;
}
