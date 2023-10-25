package ru.codesquad.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import ru.codesquad.Statistics;
import ru.codesquad.model.KennelHit;
import ru.codesquad.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = Statistics.class)
@DataJpaTest
class KennelHitRepositoryTest {

    @Autowired
    private KennelHitRepository kennelHitRepository;

    KennelHit kennelHit;
    KennelHit kennelHit2;
    KennelHit kennelHit3;
    KennelHit kennelHit4;
    LocalDateTime start;
    LocalDateTime end;
    String[] uris;

    @BeforeEach
    public void addData() {
        kennelHit = kennelHitRepository.save(KennelHit.builder()
                .app("bb-servise")
                .uri("/kennels/1")
                .ip("192.158.1.38")
                .timestamp(LocalDateTime.now())
                .build());
        kennelHit2 = kennelHitRepository.save(KennelHit.builder()
                .app("bb-servise")
                .uri("/kennels/1")
                .ip("192.158.1.39")
                .timestamp(LocalDateTime.now())
                .build());
        kennelHit3 = kennelHitRepository.save(KennelHit.builder()
                .app("bb-servise")
                .uri("/kennels/2")
                .ip("192.158.1.38")
                .timestamp(LocalDateTime.now())
                .build());
        kennelHit4 = kennelHitRepository.save(KennelHit.builder()
                .app("bb-servise")
                .uri("/kennels/2")
                .ip("192.158.1.38")
                .timestamp(LocalDateTime.now())
                .build());
        start = LocalDateTime.of(2020, 5, 5, 0, 0, 0);
        end = LocalDateTime.of(2035, 5, 5, 0, 0, 0);
        uris = new String[]{"/kennels/1"};
    }

    @Test
    void findAllNonUniqueVisitsWithUris() {
        List<Stats> stats = kennelHitRepository.findAllNonUniqueVisitsWithUris(start, end, uris);

        assertEquals(1, stats.size());
        assertEquals(2, stats.get(0).getHits());
    }

    @Test
    void findAllUniqueVisitsWithUris() {
        List<Stats> stats = kennelHitRepository.findAllUniqueVisitsWithUris(start, end, uris);

        assertEquals(1, stats.size());
        assertEquals(2, stats.get(0).getHits());
    }

    @Test
    void findAllNonUniqueVisitsWithoutUris() {
        List<Stats> stats = kennelHitRepository.findAllNonUniqueVisitsWithoutUris(start, end);

        assertEquals(2, stats.size());
        assertEquals(2, stats.get(0).getHits());
    }

    @Test
    void findAllUniqueVisitsWithoutUris() {
        List<Stats> stats = kennelHitRepository.findAllUniqueVisitsWithoutUris(start, end);
        System.out.println(stats);
        assertEquals(2, stats.size());
        assertEquals(1, stats.get(1).getHits());
    }

    @AfterEach
    public void deleteData() {
        kennelHitRepository.deleteAll();
    }
}