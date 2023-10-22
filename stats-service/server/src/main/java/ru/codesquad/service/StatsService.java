package ru.codesquad.service;

import ru.codesquad.dto.HitDto;
import ru.codesquad.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    void save(HitDto hitDto);

    List<StatsDto> getStatsWithUris(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique);

    List<StatsDto> getStatsWithoutUris(LocalDateTime start, LocalDateTime end, boolean unique);
}
