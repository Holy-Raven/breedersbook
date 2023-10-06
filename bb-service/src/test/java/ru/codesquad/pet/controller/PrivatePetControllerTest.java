package ru.codesquad.pet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.codesquad.breed.dto.BreedShortDto;
import ru.codesquad.exception.NotFoundException;
import ru.codesquad.kennel.dto.KennelDto;
import ru.codesquad.pet.dto.PetFullDto;
import ru.codesquad.pet.dto.PetNewDto;
import ru.codesquad.pet.dto.PetUpdateDto;
import ru.codesquad.pet.enums.Color;
import ru.codesquad.pet.enums.SaleStatus;
import ru.codesquad.pet.model.Pet;
import ru.codesquad.pet.service.PetService;
import ru.codesquad.user.User;
import ru.codesquad.user.dto.UserShortDto;
import ru.codesquad.userinfo.dto.UserInfoDto;
import ru.codesquad.util.enums.Gender;
import ru.codesquad.util.enums.PetType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PrivatePetController.class)
class PrivatePetControllerTest {

    private static final String URL = "/private/pets";

    @Autowired
    ObjectMapper mapper;

    @MockBean
    PetService service;

    @Autowired
    private MockMvc mvc;

    PetFullDto petFullDto;
    PetNewDto petNewDto;
    PetUpdateDto petUpdateDto;

    UserShortDto owner;
    UserInfoDto userInfo;

    BreedShortDto breed;

    KennelDto kennel;

    PetFullDto.PetFullDtoBuilder fullDtoBuilder;
    PetNewDto.PetNewDtoBuilder newDtoBuilder;
    PetUpdateDto.PetUpdateDtoBuilder updateDtoBuilder;


    @BeforeEach
    void setup() {
        userInfo = UserInfoDto.builder()
                .birthDate(LocalDateTime.now().minusYears(30))
                .description("Description")
                .phone("+79001234567")
                .photo("https://photo-url.com")
                .build();
        owner = UserShortDto.builder()
                .name("Name")
                .gender(Gender.FEMALE)
                .userInfo(userInfo)
                .build();
        breed = BreedShortDto.builder()
                .id(1)
                .name("NoName")
                .build();
        kennel = KennelDto.builder()
                .id(1L)
                .name("Name")
                .build();
        fullDtoBuilder = PetFullDto.builder()
                .id(1L)
                .owner(owner)
                .saleStatus(SaleStatus.FOR_SALE)
                .price(100)
                .temper("Angry small evil")
                .petType(PetType.CAT)
                .colors(List.of(Color.BLACK))
                .pattern("SOLID")
                .breed(breed)
                .name("Name")
                .description("Description")
                .sterilized(false)
                .passportImg("https://passport-url.com")
                .gender(Gender.FEMALE)
                .birthDate(LocalDate.now().minusMonths(3))
                .kennel(kennel);
        newDtoBuilder = PetNewDto.builder()
                .petType("cat")
                .breedId(1L)
                .gender("female")
                .pattern("solid")
                .colors(List.of("black"))
                .birthDate(LocalDate.now().minusMonths(1))
                .name("Name")
                .description("Description")
                .forSale(false)
                .passportImg("https://passport-url.com")
                .temper("Angry small evil");
        updateDtoBuilder = PetUpdateDto.builder()
                .description("DescriptionUpdate")
                .saleStatus("for_sale")
                .colors(List.of("black", "white"));
    }

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mvc);
    }

    @Test
    void shouldGetByUserId() throws Exception {
        //Empty List
        when(service.getAllByUserId(anyLong(), any(), any(), any(), anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());
        mvc.perform(get(URL)
                        .header("B-B-User-Id", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        //Single List
        petFullDto = fullDtoBuilder.build();
        when(service.getAllByUserId(anyLong(), any(), any(), any(), anyInt(), anyInt()))
                .thenReturn(List.of(petFullDto));
        mvc.perform(get(URL)
                        .header("B-B-User-Id", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void shouldFailGetByUserId() throws Exception {
        //fail by not found user
        when(service.getAllByUserId(anyLong(), any(), any(), any(), anyInt(), anyInt()))
                .thenThrow(new NotFoundException(User.class, "User Not Found"));
        mvc.perform(get(URL)
                        .header("B-B-User-Id", 1))
                .andDo(print())
                .andExpect(status().isNotFound());

        //fail by absence of header
        mvc.perform(get(URL))
                .andDo(print())
                .andExpect(status().isBadRequest());

        //fail by validation
        mvc.perform(get(URL)
                        .param("gender", "unknown"))
                .andDo(print())
                .andExpect(status().isBadRequest());

        mvc.perform(get(URL)
                        .param("saleStatus", "unknown"))
                .andDo(print())
                .andExpect(status().isBadRequest());

        mvc.perform(get(URL)
                        .param("sort", "unknown"))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldGetUsersPetById() throws Exception {
        //Regular Case
        petFullDto = fullDtoBuilder.build();
        when(service.getUsersPetById(anyLong(), anyLong())).thenReturn(petFullDto);
        mvc.perform(get(URL + "/1")
                        .header("B-B-User-Id", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(petFullDto.getId()), Long.class));
    }

    @Test
    void shouldFailGetUsersPetById() throws Exception {
        //fail by not found user
        when(service.getUsersPetById(1, 2))
                .thenThrow(new NotFoundException(User.class, "User Not Found"));
        mvc.perform(get(URL + "/2")
                        .header("B-B-User-Id", 1))
                .andDo(print())
                .andExpect(status().isNotFound());

        //fail by not found pet
        when(service.getUsersPetById(2, 1))
                .thenThrow(new NotFoundException(Pet.class, "Pet Not Found"));
        mvc.perform(get(URL + "/1")
                        .header("B-B-User-Id", 2))
                .andDo(print())
                .andExpect(status().isNotFound());

        //fail by absence of header
        mvc.perform(get(URL + "/1"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldAdd() throws Exception {
        //regular case
        petFullDto = fullDtoBuilder.build();
        petNewDto = newDtoBuilder.build();
        when(service.add(anyLong(), any())).thenReturn(petFullDto);
        String json = mapper.writeValueAsString(petNewDto);
        mvc.perform(post(URL)
                        .header("B-B-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(petFullDto.getId()), Long.class));
    }

    @Test
    void shouldFailByNotFoundUserAdd() throws Exception {
        //fail by not found user
        petNewDto = newDtoBuilder.build();
        String json = mapper.writeValueAsString(petNewDto);
        when(service.add(anyLong(), any())).thenThrow(new NotFoundException(User.class, "User Not Found"));
        mvc.perform(post(URL)
                        .header("B-B-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldFailByValidationAdd() throws Exception {
        //fail by validation
        petNewDto = newDtoBuilder.build();
        petNewDto.setPetType("unknown");
        String json = mapper.writeValueAsString(petNewDto);
        mvc.perform(post(URL)
                        .header("B-B-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());

        petNewDto.setPetType("cat");
        petNewDto.setGender("unknown");
        json = mapper.writeValueAsString(petNewDto);
        mvc.perform(post(URL)
                        .header("B-B-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());

        petNewDto.setGender("female");
        petNewDto.setName("");
        json = mapper.writeValueAsString(petNewDto);
        mvc.perform(post(URL)
                        .header("B-B-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());

        petNewDto.setName("Name");
        petNewDto.setBirthDate(LocalDate.now().plusDays(1));
        json = mapper.writeValueAsString(petNewDto);
        mvc.perform(post(URL)
                        .header("B-B-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());

        petNewDto.setBirthDate(LocalDate.now().minusMonths(3));
        petNewDto.setDescription("");
        json = mapper.writeValueAsString(petNewDto);
        mvc.perform(post(URL)
                        .header("B-B-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());

        petNewDto.setDescription("Description");
        petNewDto.setBreedId(null);
        json = mapper.writeValueAsString(petNewDto);
        mvc.perform(post(URL)
                        .header("B-B-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());

        petNewDto.setBreedId(1L);
        petNewDto.setTemper("");
        json = mapper.writeValueAsString(petNewDto);
        mvc.perform(post(URL)
                        .header("B-B-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());

        petNewDto.setTemper("Temper");
        petNewDto.setPattern("");
        json = mapper.writeValueAsString(petNewDto);
        mvc.perform(post(URL)
                        .header("B-B-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());

        petNewDto.setPattern("solid");
        petNewDto.setColors(Collections.emptyList());
        json = mapper.writeValueAsString(petNewDto);
        mvc.perform(post(URL)
                        .header("B-B-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdate() throws Exception {
        //regular case
        petFullDto = fullDtoBuilder.build();
        petUpdateDto = updateDtoBuilder.build();
        String json = mapper.writeValueAsString(petUpdateDto);
        mvc.perform(patch(URL + "/1")
                        .header("B-B-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldFailUpdate() throws Exception {
        //fail by not found user
        petUpdateDto = updateDtoBuilder.build();
        String json = mapper.writeValueAsString(petUpdateDto);
        when(service.update(1, 2, petUpdateDto))
                .thenThrow(new NotFoundException(User.class, "User Not Found"));
        mvc.perform(patch(URL + "/2")
                        .header("B-B-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());

        //fail by not found pet
        when(service.update(2, 1, petUpdateDto))
                .thenThrow(new NotFoundException(Pet.class, "Pet Not Found"));
        mvc.perform(patch(URL + "/1")
                        .header("B-B-User-Id", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDelete() throws Exception {
        this.mvc.perform(delete(URL + "/1")
                        .header("B-B-User-Id", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldFailDelete() throws Exception {
        doThrow(new NotFoundException(User.class, "User Not Found")).when(service).deleteByUser(1, 1);
        this.mvc.perform(delete(URL + "/1")
                        .header("B-B-User-Id", 1))
                .andDo(print())
                .andExpect(status().isNotFound());

        doThrow(new NotFoundException(Pet.class, "Pet Not Found")).when(service).deleteByUser(1, 1);
        this.mvc.perform(delete(URL + "/1")
                        .header("B-B-User-Id", 1))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}