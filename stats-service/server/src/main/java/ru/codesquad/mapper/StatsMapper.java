package ru.codesquad.mapper;

import lombok.experimental.UtilityClass;
import ru.codesquad.dto.StatsDto;
import ru.codesquad.model.Stats;

@UtilityClass
public class StatsMapper {
    public StatsDto toStatsDto(Stats stats) {
        return StatsDto.builder()
                .app(stats.getApp())
                .uri(stats.getUri())
                .hits(stats.getHits())
                .build();
    }
}
