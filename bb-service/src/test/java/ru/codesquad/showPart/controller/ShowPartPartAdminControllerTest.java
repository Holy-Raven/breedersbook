package ru.codesquad.showPart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.codesquad.exception.NotFoundException;
import ru.codesquad.showPart.dto.ShowPartFullDto;
import ru.codesquad.showPart.model.ShowPart;
import ru.codesquad.showPart.service.ShowPartService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.codesquad.showPart.mapper.ShowPartMapper.returnFullDto;
import static ru.codesquad.util.TestFactory.makeFilledShow;

@WebMvcTest(controllers = ShowPartAdminController.class)
class ShowPartPartAdminControllerTest {
    private static final String URL = "/admin/shows";

    @Autowired
    ObjectMapper mapper;

    @MockBean
    ShowPartService service;

    @Autowired
    private MockMvc mvc;

    ShowPartFullDto showPartFullDto;

    @BeforeEach
    void setup() {
    }

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mvc);
    }

    @Test
    void shouldGetById() throws Exception {
        ShowPart showPart = makeFilledShow(1, 1);
        showPartFullDto = returnFullDto(showPart);
        String jsonDto = mapper.writeValueAsString(showPartFullDto);
        when(service.getById(anyLong())).thenReturn(showPartFullDto);
        mvc.perform(get(URL + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonDto));
    }

    @Test
    void shouldFailGetById() throws Exception {
        when(service.getById(anyLong())).thenThrow(new NotFoundException(ShowPart.class, "Участие в выставке не найдено"));
        mvc.perform(get(URL + "/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDelete() throws Exception {
        mvc.perform(delete(URL + "/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}