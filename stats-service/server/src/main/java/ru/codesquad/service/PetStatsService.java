package ru.codesquad.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.codesquad.dto.HitDto;
import ru.codesquad.dto.StatsDto;
import ru.codesquad.mapper.PetHitMapper;
import ru.codesquad.mapper.StatsMapper;
import ru.codesquad.model.PetHit;
import ru.codesquad.model.Stats;
import ru.codesquad.repository.PetHitRepository;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("petStats")
@AllArgsConstructor
public class PetStatsService implements StatsService {
    private final PetHitRepository hitRepository;

    @Override
    public void save(HitDto hitDto) {
        PetHit hit = PetHitMapper.toHit(hitDto);
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
