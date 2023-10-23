package ru.codesquad.pet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.codesquad.controller.PetStatsController;
import ru.codesquad.dto.HitDto;
import ru.codesquad.dto.StatsDto;
import ru.codesquad.service.PetStatsService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = PetStatsController.class)
@WebMvcTest(controllers = PetStatsController.class)
class PetStatsControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetStatsService petStatsService;

    @SneakyThrows
    @Test
    void saveHit() {
        HitDto hitDto = new HitDto("bb-service",
                "/pets/1",
                "192.158.1.38",
                "2023-10-10 10:10:10");
        mockMvc.perform(post("/pets/hit")
                        .content(objectMapper.writeValueAsString(hitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(petStatsService).save(hitDto);
    }

    @SneakyThrows
    @Test
    void saveHit_whenAppIsBlank_thenReturnBadRequest() {
        HitDto hitDto = new HitDto("",
                "/pets/1",
                "192.158.1.38",
                "2023-10-10 10:10:10");
        mockMvc.perform(post("/pets/hit")
                        .content(objectMapper.writeValueAsString(hitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void saveHit_whenUriIsBlank_thenReturnBadRequest() {
        HitDto hitDto = new HitDto("bb-service",
                "",
                "192.158.1.38",
                "2023-10-10 10:10:10");
        mockMvc.perform(post("/pets/hit")
                        .content(objectMapper.writeValueAsString(hitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void saveHit_whenIpIsBlank_thenReturnBadRequest() {
        HitDto hitDto = new HitDto("bb-service",
                "/pets/1",
                "",
                "2023-10-10 10:10:10");
        mockMvc.perform(post("/pets/hit")
                        .content(objectMapper.writeValueAsString(hitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void saveHit_whenTimestampIsBlank_thenReturnBadRequest() {
        HitDto hitDto = new HitDto("bb-service",
                "/pets/1",
                "192.158.1.38",
                "");
        mockMvc.perform(post("/pets/hit")
                        .content(objectMapper.writeValueAsString(hitDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void getStatsWithUris() {
        LocalDateTime start = LocalDateTime.of(2020, 5, 5, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2035, 5, 5, 0, 0, 0);
        String[] uris = {"/pets/1"};
        boolean unique = false;
        StatsDto statsDto = new StatsDto("bb-service", "/pets/1", 1);
        when(petStatsService.getStatsWithUris(start, end, uris, unique)).thenReturn(List.of(statsDto));

        mockMvc.perform(get("/pets/stats?start=2020-05-05 00:00:00&end=2035-05-05 00:00:00&uris=/pets/1&unique=false"))
                .andExpect(status().isOk());

        verify(petStatsService).getStatsWithUris(start, end, uris, unique);
    }

    @SneakyThrows
    @Test
    void getStatsWithoutUris() {
        LocalDateTime start = LocalDateTime.of(2020, 5, 5, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2035, 5, 5, 0, 0, 0);
        boolean unique = false;
        StatsDto statsDto = new StatsDto("bb-service", "/pets/1", 1);
        StatsDto statsDto2 = new StatsDto("bb-service", "/pets/2", 1);
        when(petStatsService.getStatsWithoutUris(start, end, unique)).thenReturn(List.of(statsDto, statsDto2));

        mockMvc.perform(get("/pets/stats?start=2020-05-05 00:00:00&end=2035-05-05 00:00:00&unique=false"))
                .andExpect(status().isOk());

        verify(petStatsService).getStatsWithoutUris(start, end, unique);
    }

    @SneakyThrows
    @Test
    void getStatsWithoutUris_whenStartIsNot_thenReturnBadRequest() {
        mockMvc.perform(get("/pets/stats?end=2035-05-05 00:00:00&unique=false"))
                .andExpect(status().isBadRequest());
    }
}