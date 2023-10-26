//package ru.codesquad.showPart.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import ru.codesquad.exception.NotFoundException;
//import ru.codesquad.showPart.dto.ShowPartFullDto;
//import ru.codesquad.showPart.dto.ShowPartNewDto;
//import ru.codesquad.showPart.dto.ShowPartUpdateDto;
//import ru.codesquad.showPart.model.ShowPart;
//import ru.codesquad.showPart.service.ShowPartService;
//
//import java.time.LocalDate;
//import java.util.Collections;
//import java.util.List;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.is;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static ru.codesquad.showPart.mapper.ShowPartMapper.returnFullDto;
//import static ru.codesquad.util.TestFactory.*;
//
//@WebMvcTest(controllers = ShowPartPrivateController.class)
//class ShowPartPartPrivateControllerTest {
//    private static final String URL = "/private/shows";
//
//    @Autowired
//    ObjectMapper mapper;
//
//    @MockBean
//    ShowPartService service;
//
//    @Autowired
//    private MockMvc mvc;
//
//    ShowPartFullDto showPartFullDto;
//
//
//    @BeforeEach
//    void setup() {
//        ShowPart showPart = makeFilledShow(1, 1);
//        showPartFullDto = returnFullDto(showPart);
//    }
//
//    @Test
//    void shouldCreateMockMvc() {
//        assertNotNull(mvc);
//    }
//
//    @Test
//    void shouldGetEmptyListByPetId() throws Exception {
//        when(service.getByPetId(anyLong(), anyLong(), anyBoolean(), anyInt(), anyInt()))
//                .thenReturn(Collections.emptyList());
//        mvc.perform(get(URL + "/pets/1")
//                        .header("B-B-User-Id", 1))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(0)));
//    }
//
//    @Test
//    void shouldGetSingleListByPetId() throws Exception {
//        when(service.getByPetId(anyLong(), anyLong(), anyBoolean(), anyInt(), anyInt()))
//                .thenReturn(List.of(showPartFullDto));
//        mvc.perform(get(URL + "/pets/1")
//                        .header("B-B-User-Id", 1))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)));
//    }
//
//    @Test
//    void shouldGetListByPetId() throws Exception {
//        ShowPart showPart2 = makeFilledShow(2, 1);
//        ShowPartFullDto showPartFullDto2 = returnFullDto(showPart2);
//
//        when(service.getByPetId(anyLong(), anyLong(), anyBoolean(), anyInt(), anyInt()))
//                .thenReturn(List.of(showPartFullDto, showPartFullDto2));
//        mvc.perform(get(URL + "/pets/1")
//                        .header("B-B-User-Id", 1))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)));
//    }
//
//    @Test
//    void shouldGetById() throws Exception {
//        String jsonDto = mapper.writeValueAsString(showPartFullDto);
//        when(service.getByUserById(anyLong(), anyLong())).thenReturn(showPartFullDto);
//        mvc.perform(get(URL + "/1")
//                        .header("B-B-User-Id", 1))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().json(jsonDto));
//    }
//
//    @Test
//    void shouldFailGetById() throws Exception {
//        when(service.getByUserById(anyLong(), anyLong()))
//                .thenThrow(new NotFoundException(ShowPart.class, "Участие в выставке не найдено"));
//        mvc.perform(get(URL + "/1")
//                        .header("B-B-User-Id", 1))
//                .andDo(print())
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void shouldAdd() throws Exception {
//        ShowPartNewDto newDto = makeNewShowDto(1);
//        String json = mapper.writeValueAsString(newDto);
//        when(service.add(anyLong(), anyLong(), any())).thenReturn(showPartFullDto);
//        mvc.perform(post(URL + "/pets/1")
//                        .header("B-B-User-Id", 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id", is(1)));
//    }
//
//    @Test
//    void shouldFailAdd() throws Exception {
//        ShowPartNewDto newDto = makeNewShowDto(1);
//        newDto.setDate(LocalDate.now().plusDays(1));
//        String json = mapper.writeValueAsString(newDto);
//        mvc.perform(post(URL + "/pets/1")
//                        .header("B-B-User-Id", 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andDo(print())
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldPatch() throws Exception {
//        ShowPartUpdateDto showPartUpdateDto = makeUpdateDto(1);
//        String json = mapper.writeValueAsString(showPartUpdateDto);
//        when(service.update(anyLong(), anyLong(), any())).thenReturn(showPartFullDto);
//        mvc.perform(patch(URL + "/1")
//                        .header("B-B-User-Id", 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void shouldFailPatch() throws Exception {
//        ShowPartUpdateDto showPartUpdateDto = makeUpdateDto(1);
//        showPartUpdateDto.setShowRank("");
//        String json = mapper.writeValueAsString(showPartUpdateDto);
//        mvc.perform(patch(URL + "/1")
//                        .header("B-B-User-Id", 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andDo(print())
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldDelete() throws Exception {
//        mvc.perform(delete(URL + "/1")
//                        .header("B-B-User-Id", 1))
//                .andDo(print())
//                .andExpect(status().isNoContent());
//    }
//}