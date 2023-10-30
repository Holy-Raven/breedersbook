package ru.codesquad.location;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import ru.codesquad.kennel.Kennel;
import ru.codesquad.kennel.KennelRepository;
import ru.codesquad.user.User;
import ru.codesquad.user.UserRepository;
import ru.codesquad.user.UserService;
import ru.codesquad.userinfo.UserInfo;
import ru.codesquad.util.UnionService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static ru.codesquad.util.TestFactory.*;

@RequiredArgsConstructor
@SpringBootTest
public class LocationServiceTest {

    @Autowired
    private LocationService locationService;

    @MockBean
    private LocationRepository locationRepository;

    @MockBean
    private KennelRepository kennelRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UnionService unionService;

    private User user;

    private Kennel kennel;

    private UserInfo userInfo;

    private Location location;

    private LocationDto locationDto;

    private LocationUpdateDto locationUpdateDto;

    @BeforeEach
    void beforeEach() {

        location = makeNewLocation(1);
        locationDto = LocationMapper.returnLocationDto(location);

        locationUpdateDto = LocationUpdateDto.builder()
                .street("Kosmonavtov")
                .house("9")
                .apartment(65)
                .build();

        userInfo = makeNewUserInfo(1);

        kennel = makeNewKennel(1);
        kennel.setId(1L);

        user = makeNewUser(userInfo, kennel, 1);
        user.setId(1L);
    }


    @Test
    void addUserLocation() {

        when(unionService.getUserOrNotFound(anyLong())).thenReturn(user);
        when(locationRepository.save(any(Location.class))).thenReturn(location);
        when(userRepository.save(any(User.class))).thenReturn(user);

        LocationDto locationDtoDtoTest = locationService.addUserLocation(user.getId(), locationDto);

        assertEquals(locationDtoDtoTest.getCountry(), locationDto.getCountry());
        assertEquals(locationDtoDtoTest.getCity(), locationDto.getCity());
        assertEquals(locationDtoDtoTest.getStreet(), locationDto.getStreet());
        assertEquals(locationDtoDtoTest.getHouse(), locationDto.getHouse());
        assertEquals(locationDtoDtoTest.getApartment(), locationDto.getApartment());

        verify(locationRepository, times(1)).save(location);
    }

    @Test
    void addKennelLocation() {

        when(unionService.getUserOrNotFound(anyLong())).thenReturn(user);
        when(locationRepository.save(any(Location.class))).thenReturn(location);
        when(kennelRepository.save(any(Kennel.class))).thenReturn(kennel);

        LocationDto locationDtoDtoTest = locationService.addKennelLocation(user.getId(), locationDto);

        assertEquals(locationDtoDtoTest.getCountry(), locationDto.getCountry());
        assertEquals(locationDtoDtoTest.getCity(), locationDto.getCity());
        assertEquals(locationDtoDtoTest.getStreet(), locationDto.getStreet());
        assertEquals(locationDtoDtoTest.getHouse(), locationDto.getHouse());
        assertEquals(locationDtoDtoTest.getApartment(), locationDto.getApartment());

        verify(locationRepository, times(1)).save(location);
    }

    @Test
    void addKennelDefaultLocation() {

        when(unionService.getUserOrNotFound(anyLong())).thenReturn(user);
        user.setLocation(location);
        when(locationRepository.save(any(Location.class))).thenReturn(location);
        when(kennelRepository.save(any(Kennel.class))).thenReturn(kennel);

        LocationDto locationDtoDtoTest = locationService.addKennelDefaultLocation(user.getId());

        assertEquals(locationDtoDtoTest.getCountry(), locationDto.getCountry());
        assertEquals(locationDtoDtoTest.getCity(), locationDto.getCity());
        assertEquals(locationDtoDtoTest.getStreet(), locationDto.getStreet());
        assertEquals(locationDtoDtoTest.getHouse(), locationDto.getHouse());
        assertEquals(locationDtoDtoTest.getApartment(), locationDto.getApartment());

        verify(locationRepository, times(1)).save(location);
    }

    @Test
    void updateUserLocation() {

        when(unionService.getUserOrNotFound(anyLong())).thenReturn(user);
        when(locationRepository.save(any(Location.class))).thenReturn(location);
        location.setId(1L);
        user.setLocation(location);

        LocationDto locationDtoDtoTest = locationService.updateUserLocation(user.getId(), locationUpdateDto);

        assertEquals(locationDtoDtoTest.getCountry(), locationDto.getCountry());
        assertEquals(locationDtoDtoTest.getCity(), locationDto.getCity());
        assertEquals(locationDtoDtoTest.getStreet(), locationUpdateDto.getStreet());
        assertEquals(locationDtoDtoTest.getHouse(), locationUpdateDto.getHouse());
        assertEquals(locationDtoDtoTest.getApartment(), locationUpdateDto.getApartment());

        verify(locationRepository, times(1)).save(location);
    }

    @Test
    void updateKennelLocation() {

        when(unionService.getUserOrNotFound(anyLong())).thenReturn(user);
        location.setId(1L);
        kennel.setLocation(location);
        when(locationRepository.save(any(Location.class))).thenReturn(location);

        LocationDto locationDtoDtoTest = locationService.updateKennelLocation(user.getId(), locationUpdateDto);

        assertEquals(locationDtoDtoTest.getCountry(), locationDto.getCountry());
        assertEquals(locationDtoDtoTest.getCity(), locationDto.getCity());
        assertEquals(locationDtoDtoTest.getStreet(), locationUpdateDto.getStreet());
        assertEquals(locationDtoDtoTest.getHouse(), locationUpdateDto.getHouse());
        assertEquals(locationDtoDtoTest.getApartment(), locationUpdateDto.getApartment());

        verify(locationRepository, times(1)).save(location);
    }

    @Test
    void deleteUserLocation() {

        when(unionService.getUserOrNotFound(anyLong())).thenReturn(user);
        location.setId(1L);
        user.setLocation(location);
        when(unionService.getLocationOrNotFound(anyLong())).thenReturn(location);

        locationService.deleteUserLocation(1L);

        verify(locationRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteKennelLocation() {

        when(unionService.getUserOrNotFound(anyLong())).thenReturn(user);
        location.setId(1L);
        kennel.setLocation(location);
        when(unionService.getLocationOrNotFound(anyLong())).thenReturn(location);

        locationService.deleteKennelLocation(1L);

        verify(locationRepository, times(1)).deleteById(1L);
    }
}
