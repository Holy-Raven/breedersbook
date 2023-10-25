package ru.codesquad.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.codesquad.dto.HitDto;
import ru.codesquad.dto.StatsDto;
import ru.codesquad.enums.Entity;
import ru.codesquad.exception.NotFoundException;
import ru.codesquad.mapper.KennelHitMapper;
import ru.codesquad.mapper.PetHitMapper;
import ru.codesquad.mapper.StatsMapper;
import ru.codesquad.model.KennelHit;
import ru.codesquad.model.PetHit;
import ru.codesquad.model.Stats;
import ru.codesquad.repository.KennelHitRepository;
import ru.codesquad.repository.PetHitRepository;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final PetHitRepository petHitRepository;
    private final KennelHitRepository kennelHitRepository;

    @Override
    public void save(HitDto hitDto, String entity) {
        switch (Entity.valueOf(entity.toUpperCase())) {
            case PET:
                PetHit petHit = PetHitMapper.toHit(hitDto);
                petHitRepository.save(petHit);
                break;
            case KENNEL:
                KennelHit kennelHit = KennelHitMapper.toHit(hitDto);
                kennelHitRepository.save(kennelHit);
                break;
            default:
                throw new NotFoundException(Entity.class, "No such entity was found. " +
                        "Viewing was not included in statistics");
        }
    }

    @Override
    public List<StatsDto> getStatsWithUris(String entity, LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        if (start.isAfter(end)) {
            throw new DateTimeException("Start is after end");
        }
        List<StatsDto> statsByRequest = new ArrayList<>();
        switch (Entity.valueOf(entity.toUpperCase())) {
            case PET:
                if (!(unique)) {
                    for (Stats stats : petHitRepository.findAllNonUniqueVisitsWithUris(start, end, uris)) {
                        StatsDto statsDto = StatsMapper.toStatsDto(stats);
                        statsByRequest.add(statsDto);
                    }
                } else {
                    for (Stats stats : petHitRepository.findAllUniqueVisitsWithUris(start, end, uris)) {
                        StatsDto statsDto = StatsMapper.toStatsDto(stats);
                        statsByRequest.add(statsDto);
                    }
                }
                return statsByRequest;
            case KENNEL:
                if (!(unique)) {
                    for (Stats stats : kennelHitRepository.findAllNonUniqueVisitsWithUris(start, end, uris)) {
                        StatsDto statsDto = StatsMapper.toStatsDto(stats);
                        statsByRequest.add(statsDto);
                    }
                } else {
                    for (Stats stats : kennelHitRepository.findAllUniqueVisitsWithUris(start, end, uris)) {
                        StatsDto statsDto = StatsMapper.toStatsDto(stats);
                        statsByRequest.add(statsDto);
                    }
                }
                return statsByRequest;
            default:
                throw new NotFoundException(Entity.class, "No such entity was found. " +
                        "Viewing statistics is not possible");
        }
    }

    @Override
    public List<StatsDto> getStatsWithoutUris(String entity, LocalDateTime start, LocalDateTime end, boolean unique) {
        if (start.isAfter(end)) {
            throw new DateTimeException("Start is after end");
        }
        List<StatsDto> statsByRequest = new ArrayList<>();
        switch (Entity.valueOf(entity.toUpperCase())) {
            case PET:
                if (!(unique)) {
                    for (Stats stats : petHitRepository.findAllNonUniqueVisitsWithoutUris(start, end)) {
                        StatsDto statsDto = StatsMapper.toStatsDto(stats);
                        statsByRequest.add(statsDto);
                    }
                } else {
                    for (Stats stats : petHitRepository.findAllUniqueVisitsWithoutUris(start, end)) {
                        StatsDto statsDto = StatsMapper.toStatsDto(stats);
                        statsByRequest.add(statsDto);
                    }
                }
                return statsByRequest;
            case KENNEL:
                if (!(unique)) {
                    for (Stats stats : kennelHitRepository.findAllNonUniqueVisitsWithoutUris(start, end)) {
                        StatsDto statsDto = StatsMapper.toStatsDto(stats);
                        statsByRequest.add(statsDto);
                    }
                } else {
                    for (Stats stats : kennelHitRepository.findAllUniqueVisitsWithoutUris(start, end)) {
                        StatsDto statsDto = StatsMapper.toStatsDto(stats);
                        statsByRequest.add(statsDto);
                    }
                }
                return statsByRequest;
            default:
                throw new NotFoundException(Entity.class, "No such entity was found. " +
                        "Viewing statistics is not possible");
        }
    }
}
