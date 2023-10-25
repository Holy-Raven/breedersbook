//package ru.codesquad.pet.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import ru.codesquad.pet.service.PetService;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(controllers = AdminPetController.class)
//class AdminPetControllerTest {
//    private static final String URL = "/admin/pets";
//
//    @Autowired
//    ObjectMapper mapper;
//
//    @MockBean
//    PetService service;
//
//    @Autowired
//    private MockMvc mvc;
//
//    @Test
//    void shouldCreateMockMvc() {
//        assertNotNull(mvc);
//    }
//
//    @Test
//    void shouldDelete() throws Exception {
//        this.mvc.perform(delete(URL + "/1"))
//                .andDo(print())
//                .andExpect(status().isNoContent());
//    }
//}