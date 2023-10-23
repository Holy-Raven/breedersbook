package ru.codesquad.kennel.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.codesquad.dto.HitDto;
import ru.codesquad.dto.StatsDto;
import ru.codesquad.mapper.KennelHitMapper;
import ru.codesquad.mapper.StatsMapper;
import ru.codesquad.model.Stats;
import ru.codesquad.repository.KennelHitRepository;
import ru.codesquad.service.KennelStatsService;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KennelStatsServiceTest {
    @Mock
    private KennelHitRepository kennelHitRepository;
    @InjectMocks
    private KennelStatsService kennelStatsService;

    @SneakyThrows
    @Test
    void save() {
        HitDto hitDto = new HitDto("bb-service",
                "/kennels/1",
                "192.158.1.38",
                "2023-10-10 10:10:10");
        kennelStatsService.save(hitDto);

        verify(kennelHitRepository).save(KennelHitMapper.toHit(hitDto));
    }

    @Test
    void getStatsWithUris_whenDatesAreValid_thenReturnListOfStatsDto() {
        LocalDateTime start = LocalDateTime.of(2020, 5, 5, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2035, 5, 5, 0, 0, 0);
        String[] uris = {"/kennels/1"};
        boolean unique = false;
        Stats stats = new Stats("bb-service", "/kennels/1", 1);
        when(kennelHitRepository.findAllNonUniqueVisitsWithUris(start, end, uris)).thenReturn(List.of(stats));

        List<StatsDto> actualStats = kennelStatsService.getStatsWithUris(start, end, uris, unique);

        assertEquals(List.of(StatsMapper.toStatsDto(stats)), actualStats);
    }

    @Test
    void getStatsWithUris_whenDatesAreNotValid_thenDateTimeException() {
        LocalDateTime end = LocalDateTime.of(2020, 5, 5, 0, 0, 0);
        LocalDateTime start = LocalDateTime.of(2035, 5, 5, 0, 0, 0);
        String[] uris = {"/kennels/1"};
        boolean unique = false;

        assertThrows(DateTimeException.class,
                () -> kennelStatsService.getStatsWithUris(start, end, uris, unique));
    }
}
