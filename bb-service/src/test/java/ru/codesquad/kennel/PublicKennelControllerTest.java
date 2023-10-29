package ru.codesquad.kennel;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.codesquad.kennel.controller.PublicKennelController;
import ru.codesquad.kennel.dto.KennelMapper;
import ru.codesquad.kennel.dto.KennelShortDto;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.codesquad.util.TestFactory.makeNewKennel;

@WebMvcTest(controllers = PublicKennelController.class)
public class PublicKennelControllerTest {


    @MockBean
    private KennelService kennelService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    private Kennel kennel1;
    private KennelShortDto kennelShortDto1;
    private Kennel kennel2;
    private KennelShortDto kennelShortDto2;
    @BeforeEach
    void beforeEach() {

        kennel1 = makeNewKennel(1);
        kennel1.setId(1L);
        kennelShortDto1 = KennelMapper.returnKennelShortDto(kennel1);

        kennel2 = makeNewKennel(2);
        kennel2.setId(2L);
        kennelShortDto2 = KennelMapper.returnKennelShortDto(kennel2);
    }

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mvc);
    }

    @Test
    void getKennel() throws Exception {

        when(kennelService.getPublicKennelById(anyLong())).thenReturn(kennelShortDto1);

        mvc.perform(get("/kennels/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(kennelService, times(1)).getPublicKennelById(1L);
    }


    @Test
    void getAllKennelByPublicFromParam() throws Exception {

        when(kennelService.getAllKennelByPublicFromParam(anyInt(), anyInt(), anyString(), anyLong())).thenReturn(List.of(kennelShortDto1, kennelShortDto2));

        mvc.perform(get("/kennels")
                        .param("from", "0")
                        .param("size", "10")
                        .param("type", "CAT")
                        .param("breed", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(kennelService, times(1)).getAllKennelByPublicFromParam(0, 10, "CAT", 1L);
    }
}
