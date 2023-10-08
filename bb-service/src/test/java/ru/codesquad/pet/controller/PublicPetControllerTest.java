package ru.codesquad.pet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.codesquad.pet.service.PetService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@WebMvcTest(controllers = PublicPetController.class)
class PublicPetControllerTest {

    private static final String URL = "/pets";

    @Autowired
    ObjectMapper mapper;

    @MockBean
    PetService service;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setup() {
        //заготовка для тестов публичного контроллера//
    }

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mvc);
    }

    @Test
    void getByFiltersPublic() {
    }

    @Test
    void getByIdPublic() {
    }
}