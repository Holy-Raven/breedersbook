package ru.codesquad.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.codesquad.dto.HitDto;
import ru.codesquad.dto.StatsDto;
import ru.codesquad.mapper.KennelHitMapper;
import ru.codesquad.mapper.StatsMapper;
import ru.codesquad.model.KennelHit;
import ru.codesquad.model.Stats;
import ru.codesquad.repository.KennelHitRepository;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class KennelStatsService implements StatsService {
    private final KennelHitRepository hitRepository;

    @Override
    public void save(HitDto hitDto) {
        KennelHit hit = KennelHitMapper.toHit(hitDto);
        hitRepository.save(hit);
    }

    @Override
    public List<StatsDto> getStatsWithUris(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        if (start.isAfter(end)) {
            throw new DateTimeException("Start is after end");
        }
        List<StatsDto> statsByRequest = new ArrayList<>();
        if (!(unique)) {
            for (Stats stats : hitRepository.findAllNonUniqueVisitsWithUris(start, end, uris)) {
                StatsDto statsDto = StatsMapper.toStatsDto(stats);
                statsByRequest.add(statsDto);
            }
        } else {
            for (Stats stats : hitRepository.findAllUniqueVisitsWithUris(start, end, uris)) {
                StatsDto statsDto = StatsMapper.toStatsDto(stats);
                statsByRequest.add(statsDto);
            }
        }
        return statsByRequest;
    }

    @Override
    public List<StatsDto> getStatsWithoutUris(LocalDateTime start, LocalDateTime end, boolean unique) {
        List<StatsDto> statsByRequest = new ArrayList<>();
        if (!(unique)) {
            for (Stats stats : hitRepository.findAllNonUniqueVisitsWithoutUris(start, end)) {
                StatsDto statsDto = StatsMapper.toStatsDto(stats);
                statsByRequest.add(statsDto);
            }
        } else {
            for (Stats stats : hitRepository.findAllUniqueVisitsWithoutUris(start, end)) {
                StatsDto statsDto = StatsMapper.toStatsDto(stats);
                statsByRequest.add(statsDto);
            }
        }
        return statsByRequest;
    }
}
