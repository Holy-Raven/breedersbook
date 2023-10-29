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
import ru.codesquad.kennel.dto.KennelDto;
import ru.codesquad.kennel.dto.KennelMapper;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.codesquad.util.TestFactory.makeNewKennel;

@WebMvcTest(controllers = AdminKennelController.class)
public class AdminKennelControllerTest {

    @MockBean
    private KennelService kennelService;

    @Autowired
    private MockMvc mvc;

    private Kennel kennel1;

    private KennelDto kennelDto1;

    private Kennel kennel2;

    private KennelDto kennelDto2;
    @BeforeEach
    void beforeEach() {

        kennel1 = makeNewKennel(1);
        kennel1.setId(1L);
        kennelDto1 = KennelMapper.returnKennelDto(kennel1);

        kennel2 = makeNewKennel(2);
        kennel2.setId(2L);
        kennelDto2 = KennelMapper.returnKennelDto(kennel2);
    }

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mvc);
    }

    @Test
    void deleteKennel() throws Exception {

        mvc.perform(delete("/admin/kennels/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(kennelService, times(1)).deleteKennel(1L);
    }

    @Test
    void getAllKennelByAdminFromParam() throws Exception {

        when(kennelService.getAllKennelByAdminFromParam(anyInt(), anyInt(), anyString(), anyLong())).thenReturn(List.of(kennelDto1, kennelDto2));

        mvc.perform(get("/admin/kennels")
                        .param("from", "0")
                        .param("size", "10")
                        .param("type", "CAT")
                        .param("breed", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(kennelService, times(1)).getAllKennelByAdminFromParam(0, 10, "CAT", 1L);
    }
}
