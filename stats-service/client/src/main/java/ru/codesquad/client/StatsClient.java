package ru.codesquad.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.codesquad.dto.HitDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class StatsClient extends BaseClient {
    @Value("${bb-server.url}")
    public String nameApp;
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    @Autowired
    public StatsClient(@Value("${bb-server.url}") String serverUrl,
                       RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> savePetHit(HttpServletRequest httpServletRequest) {
        HitDto hitDto = HitDto.builder()
                .app(nameApp)
                .uri(httpServletRequest.getRequestURI())
                .ip(httpServletRequest.getRemoteAddr())
                .timestamp(LocalDateTime.now().format(DATE_TIME_FORMATTER))
                .build();
        return post("/pets/hit", hitDto);
    }

    public ResponseEntity<Object> saveKennelHit(HttpServletRequest httpServletRequest) {
        HitDto hitDto = HitDto.builder()
                .app(nameApp)
                .uri(httpServletRequest.getRequestURI())
                .ip(httpServletRequest.getRemoteAddr())
                .timestamp(LocalDateTime.now().format(DATE_TIME_FORMATTER))
                .build();
        return post("/kennels/hit", hitDto);
    }

    public ResponseEntity<Object> getPetStatsWithUris(LocalDateTime start,
                                                      LocalDateTime end,
                                                      String[] uris,
                                                      boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start.format(DATE_TIME_FORMATTER),
                "end", end.format(DATE_TIME_FORMATTER),
                "uris", uris,
                "unique", unique
        );
        return get("/pets/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
    }

    public ResponseEntity<Object> getPetStatsWithoutUris(LocalDateTime start, LocalDateTime end, boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start.format(DATE_TIME_FORMATTER),
                "end", end.format(DATE_TIME_FORMATTER),
                "unique", unique
        );
        return get("/pets/stats?start={start}&end={end}&unique={unique}", parameters);
    }

    public ResponseEntity<Object> getKennelStatsWithUris(LocalDateTime start,
                                                      LocalDateTime end,
                                                      String[] uris,
                                                      boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start.format(DATE_TIME_FORMATTER),
                "end", end.format(DATE_TIME_FORMATTER),
                "uris", uris,
                "unique", unique
        );
        return get("/kennels/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
    }

    public ResponseEntity<Object> getKennelStatsWithoutUris(LocalDateTime start, LocalDateTime end, boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start.format(DATE_TIME_FORMATTER),
                "end", end.format(DATE_TIME_FORMATTER),
                "unique", unique
        );
        return get("/kennels/stats?start={start}&end={end}&unique={unique}", parameters);
    }
}