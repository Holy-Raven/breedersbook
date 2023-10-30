package ru.codesquad.kennel;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import ru.codesquad.breed.Breed;
import ru.codesquad.kennel.dto.KennelDto;
import ru.codesquad.kennel.dto.KennelMapper;
import ru.codesquad.kennel.dto.KennelNewDto;
import ru.codesquad.location.*;
import ru.codesquad.role.Role;
import ru.codesquad.role.RoleRepository;
import ru.codesquad.role.RoleService;
import ru.codesquad.user.User;
import ru.codesquad.user.UserRepository;
import ru.codesquad.userinfo.UserInfo;
import ru.codesquad.util.UnionService;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static ru.codesquad.util.TestFactory.*;

@RequiredArgsConstructor
@SpringBootTest
public class KennelServiceTest {

    @Autowired
    private KennelService kennelService;

    @MockBean
    private KennelRepository kennelRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleService roleService;

    @MockBean
    private UnionService unionService;

    private User user;

    private Kennel kennel;

    private UserInfo userInfo;

    private Location location;

    private Breed breed;

    private LocationDto locationDto;

    private KennelDto kennelDto;

    private KennelNewDto kennelNewDto;

    private Set<Role> roleSet;

    @BeforeEach
    void beforeEach() {

        location = makeNewLocation(1);
        locationDto = LocationMapper.returnLocationDto(location);

        userInfo = makeNewUserInfo(1);

        breed = makeNewBreed();
        breed.setId(1L);

        kennel = makeNewKennel(1);
        kennel.setId(1L);
        kennel.setLocation(location);
        kennel.setBreed(breed);
        kennelDto = KennelMapper.returnKennelDto(kennel);

        user = makeNewUser(userInfo, kennel, 1);
        user.setId(1L);
        user.setKennel(kennel);
        roleSet = new HashSet<>();
        roleSet.add(roleService.getUserRole());
        user.setRoles(roleSet);

        kennelNewDto = KennelNewDto.builder()
                .petType("DOG")
                .name("Name1")
                .descriptions("Description1")
                .phone("+79001234561")
                .photo("photo")
                .created(kennel.getCreated())
                .build();
    }

    @Test
    void addKennel() {

        when(unionService.getUserOrNotFound(anyLong())).thenReturn(user);
        when(unionService.getBreedOrNotFound(anyLong())).thenReturn(breed);
        when(unionService.checkPhoneNumber(anyString())).thenReturn("+79001234561");
        when(kennelRepository.save(any(Kennel.class))).thenReturn(kennel);
        when(roleService.getBreederRole()).thenReturn(new Role());
        when(userRepository.save(any(User.class))).thenReturn(user);

        KennelDto kennelDtoTest = kennelService.addKennel(user.getId(), kennelNewDto);

        assertEquals(kennelDtoTest.getName(), kennelDto.getName());
        assertEquals(kennelDtoTest.getDescriptions(), kennelDto.getDescriptions());
        assertEquals(kennelDtoTest.getPhone(), kennelDto.getPhone());
    }

    @Test
    void getPrivateKennelById() {

        when(unionService.getUserOrNotFound(anyLong())).thenReturn(user);
        when(unionService.getKennelOrNotFound(anyLong())).thenReturn(kennel);

        KennelDto kennelDtoTest = kennelService.getPrivateKennelById(kennel.getId(), user.getId());

        assertEquals(kennelDtoTest.getName(), kennelDto.getName());
        assertEquals(kennelDtoTest.getDescriptions(), kennelDto.getDescriptions());
        assertEquals(kennelDtoTest.getPhone(), kennelDto.getPhone());

        verify(unionService, times(1)).getKennelOrNotFound(kennel.getId());
    }

    @Test
    void deleteKennel() {

        when(unionService.getKennelOrNotFound(anyLong())).thenReturn(kennel);
        when(userRepository.findByKennelId(anyLong())).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(roleService.getBreederRole()).thenReturn(new Role());

        kennelRepository.deleteById(kennel.getId());

        verify(kennelRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletePrivateKennel() {

        when(unionService.getUserOrNotFound(anyLong())).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(roleService.getBreederRole()).thenReturn(new Role());

        kennelRepository.deleteById(kennel.getId());

        verify(kennelRepository, times(1)).deleteById(1L);
    }
}
