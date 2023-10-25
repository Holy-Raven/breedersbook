package ru.codesquad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.codesquad.Statistics;
import ru.codesquad.dto.HitDto;
import ru.codesquad.dto.StatsDto;
import ru.codesquad.service.StatsService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = Statistics.class)
@WebMvcTest(controllers = StatsController.class)
public class StatsControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatsService statsService;

    @SneakyThrows
    @Test
    void saveHit() {
        String entity = "kennel";
        HitDto hitDto = new HitDto("bb-service",
                "/kennels/1",
                "192.158.1.38",
                "2023-10-10 10:10:10");
        mockMvc.perform(post("/hit?entity=" + entity)
                        .content(objectMapper.writeValueAsString(hitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(statsService).save(hitDto, entity);
    }

    @SneakyThrows
    @Test
    void saveHit_whenAppIsBlank_thenReturnBadRequest() {
        String entity = "kennel";
        HitDto hitDto = new HitDto("",
                "/kennels/1",
                "192.158.1.38",
                "2023-10-10 10:10:10");
        mockMvc.perform(post("/hit?entity=" + entity)
                        .content(objectMapper.writeValueAsString(hitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void saveHit_whenUriIsBlank_thenReturnBadRequest() {
        String entity = "kennel";
        HitDto hitDto = new HitDto("bb-service",
                "",
                "192.158.1.38",
                "2023-10-10 10:10:10");
        mockMvc.perform(post("/hit?entity=" + entity)
                        .content(objectMapper.writeValueAsString(hitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void saveHit_whenIpIsBlank_thenReturnBadRequest() {
        String entity = "kennel";
        HitDto hitDto = new HitDto("bb-service",
                "/kennels/1",
                "",
                "2023-10-10 10:10:10");
        mockMvc.perform(post("/hit?entity=" + entity)
                        .content(objectMapper.writeValueAsString(hitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void saveHit_whenTimestampIsBlank_thenReturnBadRequest() {
        String entity = "kennel";
        HitDto hitDto = new HitDto("bb-service",
                "/kennels/1",
                "192.158.1.38",
                "");
        mockMvc.perform(post("/hit?entity=" + entity)
                        .content(objectMapper.writeValueAsString(hitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void getStatsWithUris() {
        String entity = "kennel";
        LocalDateTime start = LocalDateTime.of(2020, 5, 5, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2035, 5, 5, 0, 0, 0);
        String[] uris = {"/kennels/1"};
        boolean unique = false;
        StatsDto statsDto = new StatsDto("bb-service", "/kennels/1", 1);
        when(statsService.getStatsWithUris(entity, start, end, uris, unique)).thenReturn(List.of(statsDto));

        mockMvc.perform(get("/stats?entity=kennel&start=2020-05-05 00:00:00&end=2035-05-05 00:00:00&uris=/kennels/1&unique=false"))
                .andExpect(status().isOk());

        verify(statsService).getStatsWithUris(entity, start, end, uris, unique);
    }

    @SneakyThrows
    @Test
    void getStatsWithoutUris() {
        String entity = "kennel";
        LocalDateTime start = LocalDateTime.of(2020, 5, 5, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2035, 5, 5, 0, 0, 0);
        boolean unique = false;
        StatsDto statsDto = new StatsDto("bb-service", "/kennels/1", 1);
        StatsDto statsDto2 = new StatsDto("bb-service", "/kennels/2", 1);
        when(statsService.getStatsWithoutUris(entity, start, end, unique)).thenReturn(List.of(statsDto, statsDto2));

        mockMvc.perform(get("/stats?entity=kennel&start=2020-05-05 00:00:00&end=2035-05-05 00:00:00&unique=false"))
                .andExpect(status().isOk());

        verify(statsService).getStatsWithoutUris(entity, start, end, unique);
    }

    @SneakyThrows
    @Test
    void getStatsWithoutUris_whenStartIsNot_thenReturnBadRequest() {
        mockMvc.perform(get("/stats?entity=kennel&stats?end=2035-05-05 00:00:00&unique=false"))
                .andExpect(status().isBadRequest());
    }
}
