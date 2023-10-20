package ru.codesquad.show.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.codesquad.exception.NotFoundException;
import ru.codesquad.show.dto.ShowFullDto;
import ru.codesquad.show.model.Show;
import ru.codesquad.show.service.ShowService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.codesquad.util.TestFactory.makeFilledShow;
import static ru.codesquad.show.mapper.ShowMapper.returnFullDto;

@WebMvcTest(controllers = ShowAdminController.class)
class ShowAdminControllerTest {
    private static final String URL = "/admin/shows";

    @Autowired
    ObjectMapper mapper;

    @MockBean
    ShowService service;

    @Autowired
    private MockMvc mvc;

    ShowFullDto showFullDto;

    @BeforeEach
    void setup() {
    }

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mvc);
    }

    @Test
    void shouldGetById() throws Exception {
        Show show = makeFilledShow(1, 1);
        showFullDto = returnFullDto(show);
        String jsonDto = mapper.writeValueAsString(showFullDto);
        when(service.getById(anyLong())).thenReturn(showFullDto);
        mvc.perform(get(URL + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonDto));
    }

    @Test
    void shouldFailGetById() throws Exception {
        when(service.getById(anyLong())).thenThrow(new NotFoundException(Show.class, "Участие в выставке не найдено"));
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