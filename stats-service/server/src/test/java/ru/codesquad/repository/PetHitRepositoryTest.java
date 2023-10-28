package ru.codesquad.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import ru.codesquad.Statistics;
import ru.codesquad.model.PetHit;
import ru.codesquad.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = Statistics.class)
@DataJpaTest
class PetHitRepositoryTest {

    @Autowired
    private PetHitRepository petHitRepository;

    PetHit petHit;
    PetHit petHit2;
    PetHit petHit3;
    PetHit petHit4;
    LocalDateTime start;
    LocalDateTime end;
    String[] uris;

    @BeforeEach
    public void addData() {
        petHit = petHitRepository.save(PetHit.builder()
                .app("bb-servise")
                .uri("/pets/1")
                .ip("192.158.1.38")
                .timestamp(LocalDateTime.now())
                .build());
        petHit2 = petHitRepository.save(PetHit.builder()
                .app("bb-servise")
                .uri("/pets/1")
                .ip("192.158.1.39")
                .timestamp(LocalDateTime.now())
                .build());
        petHit3 = petHitRepository.save(PetHit.builder()
                .app("bb-servise")
                .uri("/pets/2")
                .ip("192.158.1.38")
                .timestamp(LocalDateTime.now())
                .build());
        petHit4 = petHitRepository.save(PetHit.builder()
                .app("bb-servise")
                .uri("/pets/2")
                .ip("192.158.1.38")
                .timestamp(LocalDateTime.now())
                .build());
        start = LocalDateTime.of(2020, 5, 5, 0, 0, 0);
        end = LocalDateTime.of(2035, 5, 5, 0, 0, 0);
        uris = new String[]{"/pets/1"};
    }

    @Test
    void findAllNonUniqueVisitsWithUris() {
        List<Stats> stats = petHitRepository.findAllNonUniqueVisitsWithUris(start, end, uris);

        assertEquals(1, stats.size());
        assertEquals(2, stats.get(0).getHits());
    }

    @Test
    void findAllUniqueVisitsWithUris() {
        List<Stats> stats = petHitRepository.findAllUniqueVisitsWithUris(start, end, uris);

        assertEquals(1, stats.size());
        assertEquals(2, stats.get(0).getHits());
    }

    @Test
    void findAllNonUniqueVisitsWithoutUris() {
        List<Stats> stats = petHitRepository.findAllNonUniqueVisitsWithoutUris(start, end);

        assertEquals(2, stats.size());
        assertEquals(2, stats.get(0).getHits());
    }

    @Test
    void findAllUniqueVisitsWithoutUris() {
        List<Stats> stats = petHitRepository.findAllUniqueVisitsWithoutUris(start, end);
        System.out.println(stats);
        assertEquals(2, stats.size());
        assertEquals(1, stats.get(1).getHits());
    }

    @AfterEach
    public void deleteData() {
        petHitRepository.deleteAll();
    }
}