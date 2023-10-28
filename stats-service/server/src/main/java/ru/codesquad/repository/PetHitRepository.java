package ru.codesquad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.codesquad.model.PetHit;
import ru.codesquad.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

public interface PetHitRepository extends JpaRepository<PetHit, Long> {
    @Query("select new ru.codesquad.model.Stats(h.app, h.uri, count(h.id)) " +
            "from PetHit as h " +
            "where ((cast(h.timestamp as date)) between (cast(?1 as date)) and (cast(?2 as date))) " +
            "and h.uri IN (?3) " +
            "group by h.app, h.uri " +
            "order by count(h.id) desc")
    List<Stats> findAllNonUniqueVisitsWithUris(LocalDateTime start, LocalDateTime end, String[] uris);

    @Query("select new ru.codesquad.model.Stats(h.app, h.uri, count(distinct h.ip)) " +
            "from PetHit as h " +
            "where ((cast(h.timestamp as date)) between (cast(?1 as date)) and (cast(?2 as date))) " +
            "and h.uri IN (?3) " +
            "group by h.app, h.uri " +
            "order by count(distinct h.ip) desc")
    List<Stats> findAllUniqueVisitsWithUris(LocalDateTime start, LocalDateTime end, String[] uris);

    @Query("select new ru.codesquad.model.Stats(h.app, h.uri, count(h.id)) " +
            "from PetHit as h " +
            "where ((cast(h.timestamp as date)) between (cast(?1 as date)) and (cast(?2 as date))) " +
            "group by h.app, h.uri " +
            "order by count(h.id) desc")
    List<Stats> findAllNonUniqueVisitsWithoutUris(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.codesquad.model.Stats(h.app, h.uri, count(distinct h.ip)) " +
            "from PetHit as h " +
            "where ((cast(h.timestamp as date)) between (cast(?1 as date)) and (cast(?2 as date))) " +
            "group by h.app, h.uri " +
            "order by count(distinct h.ip) desc")
    List<Stats> findAllUniqueVisitsWithoutUris(LocalDateTime start, LocalDateTime end);
}
