package ru.codesquad.show.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.codesquad.exception.NotFoundException;
import ru.codesquad.show.dto.ShowFullDto;
import ru.codesquad.show.dto.ShowNewDto;
import ru.codesquad.show.dto.ShowUpdateDto;
import ru.codesquad.show.model.Show;
import ru.codesquad.show.service.ShowService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.codesquad.show.mapper.ShowMapper.returnFullDto;
import static ru.codesquad.util.TestFactory.*;

@WebMvcTest(controllers = ShowPrivateController.class)
class ShowPrivateControllerTest {
    private static final String URL = "/private/shows";

    @Autowired
    ObjectMapper mapper;

    @MockBean
    ShowService service;

    @Autowired
    private MockMvc mvc;

    ShowFullDto showFullDto;


    @BeforeEach
    void setup() {
        Show show = makeFilledShow(1, 1);
        showFullDto = returnFullDto(show);
    }

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mvc);
    }

    @Test
    void shouldGetEmptyListByPetId() throws Exception {
        when(service.getByPetId(anyLong(), anyLong(), anyBoolean(), anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());
        mvc.perform(get(URL + "/pets/1")
                        .header("B-B-User-Id", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldGetSingleListByPetId() throws Exception {
        when(service.getByPetId(anyLong(), anyLong(), anyBoolean(), anyInt(), anyInt()))
                .thenReturn(List.of(showFullDto));
        mvc.perform(get(URL + "/pets/1")
                        .header("B-B-User-Id", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void shouldGetListByPetId() throws Exception {
        Show show2 = makeFilledShow(2, 1);
        ShowFullDto showFullDto2 = returnFullDto(show2);

        when(service.getByPetId(anyLong(), anyLong(), anyBoolean(), anyInt(), anyInt()))
                .thenReturn(List.of(showFullDto, showFullDto2));
        mvc.perform(get(URL + "/pets/1")
                        .header("B-B-User-Id", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldGetById() throws Exception {
        String jsonDto = mapper.writeValueAsString(showFullDto);
        when(service.getByUserById(anyLong(), anyLong())).thenReturn(showFullDto);
        mvc.perform(get(URL + "/1")
                        .header("B-B-User-Id", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonDto));
    }

    @Test
    void shouldFailGetById() throws Exception {
        when(service.getByUserById(anyLong(), anyLong()))
                .thenThrow(new NotFoundException(Show.class, "Участие в выставке не найдено"));
        mvc.perform(get(URL + "/1")
                        .header("B-B-User-Id", 1))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldAdd() throws Exception {
        ShowNewDto newDto = makeNewShowDto(1);
        String json = mapper.writeValueAsString(newDto);
        when(service.add(anyLong(), anyLong(), any())).thenReturn(showFullDto);
        mvc.perform(post(URL + "/pets/1")
                        .header("B-B-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void shouldFailAdd() throws Exception {
        ShowNewDto newDto = makeNewShowDto(1);
        newDto.setDate(LocalDate.now().plusDays(1));
        String json = mapper.writeValueAsString(newDto);
        mvc.perform(post(URL + "/pets/1")
                        .header("B-B-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldPatch() throws Exception {
        ShowUpdateDto showUpdateDto = makeUpdateDto(1);
        String json = mapper.writeValueAsString(showUpdateDto);
        when(service.update(anyLong(), anyLong(), any())).thenReturn(showFullDto);
        mvc.perform(patch(URL + "/1")
                        .header("B-B-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldFailPatch() throws Exception {
        ShowUpdateDto showUpdateDto = makeUpdateDto(1);
        showUpdateDto.setShowRank("");
        String json = mapper.writeValueAsString(showUpdateDto);
        mvc.perform(patch(URL + "/1")
                        .header("B-B-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDelete() throws Exception {
        mvc.perform(delete(URL + "/1")
                        .header("B-B-User-Id", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}