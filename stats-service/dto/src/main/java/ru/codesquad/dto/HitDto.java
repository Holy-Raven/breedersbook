package ru.codesquad.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Сущность просмотра")
public class HitDto {
    @NotBlank
    @Schema(description = "Наименование сервиса, передающего просмотр", example = "bb-service")
    private String app;
    @NotBlank
    @Schema(description = "Эндпоинт сущности, по которой сохраняется статистика", example = "/pets/1")
    private String uri;
    @NotBlank
    @Schema(description = "ip-адрес, с которого был просмотр", example = "192.158.1.38")
    private String ip;
    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Время просмотра в формате \"yyyy-MM-dd HH:mm:ss\"")
    private String timestamp;
}
