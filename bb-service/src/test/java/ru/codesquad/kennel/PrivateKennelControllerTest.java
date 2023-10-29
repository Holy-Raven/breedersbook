package ru.codesquad.kennel;



import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.codesquad.kennel.controller.AdminKennelController;
import ru.codesquad.kennel.controller.PrivateKennelController;
import ru.codesquad.kennel.dto.KennelDto;
import ru.codesquad.kennel.dto.KennelMapper;
import ru.codesquad.kennel.dto.KennelNewDto;
import ru.codesquad.kennel.dto.KennelUpdateDto;
import ru.codesquad.util.enums.PetType;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.codesquad.util.TestFactory.makeNewKennel;
import static ru.codesquad.util.Constant.HEADER_USER;
import static ru.codesquad.util.TestFactory.makeNewKennel;

@WebMvcTest(controllers = PrivateKennelController.class)
public class PrivateKennelControllerTest {


    @MockBean
    private KennelService kennelService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    private Kennel kennel1;

    private KennelDto kennelDto1;

    private Kennel kennel2;

    private KennelDto kennelDto2;

    private KennelNewDto kennelNewDto;

    private KennelUpdateDto kennelUpdateDto;


    @BeforeEach
    void beforeEach() {

        kennel1 = makeNewKennel(1);
        kennel1.setId(1L);
        kennelDto1 = KennelMapper.returnKennelDto(kennel1);

        kennel2 = makeNewKennel(2);
        kennel2.setId(2L);
        kennelDto2 = KennelMapper.returnKennelDto(kennel2);

        kennelNewDto = KennelNewDto.builder()
                .petType("DOG")
                .name("Name1")
                .descriptions("Description1")
                .phone("+79001234561")
                .photo("photo")
                .created(kennel1.getCreated())
                .build();

        kennelUpdateDto = KennelUpdateDto.builder()
                .name("Name10")
                .descriptions("Description10")
                .phone("+79001234565")
                .build();

    }

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mvc);
    }

    @Test
    void addKennel() throws Exception {

        when(kennelService.addKennel(anyLong(), any(KennelNewDto.class))).thenReturn(kennelDto1);

        mvc.perform(post("/private/kennels")
                        .header(HEADER_USER, 1)
                        .content(mapper.writeValueAsString(kennelNewDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(kennelDto1.getName()), String.class))
                .andExpect(jsonPath("$.descriptions", is(kennelDto1.getDescriptions()), String.class))
                .andExpect(jsonPath("$.phone", is(kennelDto1.getPhone()), String.class))
                .andExpect(jsonPath("$.photo", is(kennelDto1.getPhoto()), String.class));
    }

    @Test
    void getKennel() throws Exception {

        when(kennelService.getPrivateKennelById(anyLong(), anyLong())).thenReturn(kennelDto1);

        mvc.perform(get("/private/kennels/1")
                        .header(HEADER_USER, 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(kennelDto1.getName()), String.class))
                .andExpect(jsonPath("$.descriptions", is(kennelDto1.getDescriptions()), String.class))
                .andExpect(jsonPath("$.phone", is(kennelDto1.getPhone()), String.class))
                .andExpect(jsonPath("$.photo", is(kennelDto1.getPhoto()), String.class));

        verify(kennelService, times(1)).getPrivateKennelById(1L, 1L);
    }

    @Test
    void deleteKennel() throws Exception {

        mvc.perform(delete("/private/kennels")
                .header(HEADER_USER, 1))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(kennelService, times(1)).deletePrivateKennel(1L);
    }


    @Test
    void updateKennel() throws Exception {

        when(kennelService.updateKennel(anyLong(), any(KennelUpdateDto.class))).thenReturn(kennelDto1);

        mvc.perform(patch("/private/kennels")
                        .header(HEADER_USER, 1)
                        .content(mapper.writeValueAsString(kennelUpdateDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(kennelDto1.getName()), String.class))
                .andExpect(jsonPath("$.descriptions", is(kennelDto1.getDescriptions()), String.class))
                .andExpect(jsonPath("$.phone", is(kennelDto1.getPhone()), String.class))
                .andExpect(jsonPath("$.photo", is(kennelDto1.getPhoto()), String.class));

        verify(kennelService, times(1)).updateKennel(1L, kennelUpdateDto);
    }
}
