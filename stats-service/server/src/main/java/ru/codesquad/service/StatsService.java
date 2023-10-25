package ru.codesquad.service;

import ru.codesquad.dto.HitDto;
import ru.codesquad.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    void save(HitDto hitDto, String entity);

    List<StatsDto> getStatsWithUris(String entity, LocalDateTime start, LocalDateTime end, String[] uris, boolean unique);

    List<StatsDto> getStatsWithoutUris(String entity, LocalDateTime start, LocalDateTime end, boolean unique);
}
