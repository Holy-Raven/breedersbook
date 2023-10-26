package ru.codesquad.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = LocationController.class)
public class LocationControllerTest {

    @MockBean
    private LocationService locationService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    private LocationDto UserLocationDto;

    private LocationDto KennelLocationDto;

    private LocationUpdateDto locationUpdateDto;

    private LocationDto newLocation;

    @BeforeEach
    void beforeEach() {

        UserLocationDto = LocationDto.builder()
                .country("Russia")
                .city("Samara")
                .street("Mira")
                .house("15")
                .apartment(135)
                .build();

        KennelLocationDto = LocationDto.builder()
                .country("Russia")
                .city("Kazan")
                .street("Kama")
                .house("10")
                .apartment(56)
                .build();

        locationUpdateDto = LocationUpdateDto.builder()
                .street("Kosmonavtov")
                .house("9")
                .apartment(65)
                .build();

        newLocation = LocationDto.builder()
                .country("Russia")
                .city("Samara")
                .street("Kosmonavtov")
                .house("9")
                .apartment(65)
                .build();
    }

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mvc);
    }

    @Test
    void addUserLocation() throws Exception {
        when(locationService.addUserLocation(anyLong(), any(LocationDto.class))).thenReturn(UserLocationDto);

        mvc.perform(post("/private/location/user")
                        .header("B-B-User-Id", 1)
                        .content(mapper.writeValueAsString(UserLocationDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.country", is(UserLocationDto.getCountry()), String.class))
                .andExpect(jsonPath("$.city", is(UserLocationDto.getCity()), String.class))
                .andExpect(jsonPath("$.street", is(UserLocationDto.getStreet()), String.class))
                .andExpect(jsonPath("$.house", is(UserLocationDto.getHouse()), String.class))
                .andExpect(jsonPath("$.apartment", is(UserLocationDto.getApartment()), Integer.class));

        verify(locationService, times(1)).addUserLocation(1L, UserLocationDto);
    }

    @Test
    void addKennelLocation() throws Exception {
        when(locationService.addKennelLocation(anyLong(), any(LocationDto.class))).thenReturn(KennelLocationDto);

        mvc.perform(post("/private/location/kennel")
                        .header("B-B-User-Id", 1)
                        .content(mapper.writeValueAsString(KennelLocationDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.country", is(KennelLocationDto.getCountry()), String.class))
                .andExpect(jsonPath("$.city", is(KennelLocationDto.getCity()), String.class))
                .andExpect(jsonPath("$.street", is(KennelLocationDto.getStreet()), String.class))
                .andExpect(jsonPath("$.house", is(KennelLocationDto.getHouse()), String.class))
                .andExpect(jsonPath("$.apartment", is(KennelLocationDto.getApartment()), Integer.class));

        verify(locationService, times(1)).addKennelLocation(1L, KennelLocationDto);
    }

    @Test
    void addKennelDefaultLocation() throws Exception {
        when(locationService.addKennelDefaultLocation(anyLong())).thenReturn(UserLocationDto);

        mvc.perform(post("/private/location/kennel/default")
                        .header("B-B-User-Id", 1)
                        .content(mapper.writeValueAsString(UserLocationDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.country", is(UserLocationDto.getCountry()), String.class))
                .andExpect(jsonPath("$.city", is(UserLocationDto.getCity()), String.class))
                .andExpect(jsonPath("$.street", is(UserLocationDto.getStreet()), String.class))
                .andExpect(jsonPath("$.house", is(UserLocationDto.getHouse()), String.class))
                .andExpect(jsonPath("$.apartment", is(UserLocationDto.getApartment()), Integer.class));

        verify(locationService, times(1)).addKennelDefaultLocation(1L);
    }

    @Test
    void updateUserLocation() throws Exception {
        when(locationService.updateUserLocation(anyLong(), any(LocationUpdateDto.class))).thenReturn(newLocation);

        mvc.perform(patch("/private/location/user")
                        .header("B-B-User-Id", 1)
                        .content(mapper.writeValueAsString(locationUpdateDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country", is(newLocation.getCountry()), String.class))
                .andExpect(jsonPath("$.city", is(newLocation.getCity()), String.class))
                .andExpect(jsonPath("$.street", is(newLocation.getStreet()), String.class))
                .andExpect(jsonPath("$.house", is(newLocation.getHouse()), String.class))
                .andExpect(jsonPath("$.apartment", is(newLocation.getApartment()), Integer.class));

        verify(locationService, times(1)).updateUserLocation(1L, locationUpdateDto);
    }

    @Test
    void updateKennelLocation() throws Exception {
        when(locationService.updateKennelLocation(anyLong(), any(LocationUpdateDto.class))).thenReturn(newLocation);

        mvc.perform(patch("/private/location/kennel")
                        .header("B-B-User-Id", 1)
                        .content(mapper.writeValueAsString(locationUpdateDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country", is(newLocation.getCountry()), String.class))
                .andExpect(jsonPath("$.city", is(newLocation.getCity()), String.class))
                .andExpect(jsonPath("$.street", is(newLocation.getStreet()), String.class))
                .andExpect(jsonPath("$.house", is(newLocation.getHouse()), String.class))
                .andExpect(jsonPath("$.apartment", is(newLocation.getApartment()), Integer.class));

        verify(locationService, times(1)).updateKennelLocation(1L, locationUpdateDto);
    }

    @Test
    void deleteUserLocation() throws Exception {

        mvc.perform(delete("/private/location/user")
                        .header("B-B-User-Id", 1))
                .andExpect(status().isNoContent());

        verify(locationService, times(1)).deleteUserLocation(1L);
    }

    @Test
    void deleteKennelLocation() throws Exception {

        mvc.perform(delete("/private/location/kennel")
                        .header("B-B-User-Id", 1))
                .andExpect(status().isNoContent());

        verify(locationService, times(1)).deleteKennelLocation(1L);
    }
}
