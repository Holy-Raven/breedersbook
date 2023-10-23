package ru.codesquad.pet.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.codesquad.dto.HitDto;
import ru.codesquad.dto.StatsDto;
import ru.codesquad.mapper.PetHitMapper;
import ru.codesquad.mapper.StatsMapper;
import ru.codesquad.model.Stats;
import ru.codesquad.repository.PetHitRepository;
import ru.codesquad.service.PetStatsService;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PetStatsServiceTest {
    @Mock
    private PetHitRepository petHitRepository;
    @InjectMocks
    private PetStatsService petStatsService;

    @SneakyThrows
    @Test
    void save() {
        HitDto hitDto = new HitDto("bb-service",
                "/pets/1",
                "192.158.1.38",
                "2023-10-10 10:10:10");
        petStatsService.save(hitDto);

        verify(petHitRepository).save(PetHitMapper.toHit(hitDto));
    }

    @Test
    void getStatsWithUris_whenDatesAreValid_thenReturnListOfStatsDto() {
        LocalDateTime start = LocalDateTime.of(2020, 5, 5, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2035, 5, 5, 0, 0, 0);
        String[] uris = {"/pets/1"};
        boolean unique = false;
        Stats stats = new Stats("bb-service", "/pets/1", 1);
        when(petHitRepository.findAllNonUniqueVisitsWithUris(start, end, uris)).thenReturn(List.of(stats));

        List<StatsDto> actualStats = petStatsService.getStatsWithUris(start, end, uris, unique);

        assertEquals(List.of(StatsMapper.toStatsDto(stats)), actualStats);
    }

    @Test
    void getStatsWithUris_whenDatesAreNotValid_thenDateTimeException() {
        LocalDateTime end = LocalDateTime.of(2020, 5, 5, 0, 0, 0);
        LocalDateTime start = LocalDateTime.of(2035, 5, 5, 0, 0, 0);
        String[] uris = {"/pets/1"};
        boolean unique = false;

        assertThrows(DateTimeException.class,
                () -> petStatsService.getStatsWithUris(start, end, uris, unique));
    }
}
