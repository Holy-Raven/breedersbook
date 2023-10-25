package ru.codesquad.mapper;

import ru.codesquad.dto.StatsDto;
import ru.codesquad.model.Stats;
public class StatsMapper {
    public static StatsDto toStatsDto(Stats stats) {
        return StatsDto.builder()
                .app(stats.getApp())
                .uri(stats.getUri())
                .hits(stats.getHits())
                .build();
    }
}
