package ru.codesquad.kennel;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import ru.codesquad.breed.Breed;
import ru.codesquad.kennel.dto.*;
import ru.codesquad.location.*;
import ru.codesquad.role.Role;
import ru.codesquad.role.RoleService;
import ru.codesquad.user.User;
import ru.codesquad.user.UserRepository;
import ru.codesquad.userinfo.UserInfo;
import ru.codesquad.util.UnionService;
import ru.codesquad.util.enums.EnumUtil;
import ru.codesquad.util.enums.PetType;

import java.util.HashSet;
import java.util.List;
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

    private Kennel kennel2;

    private Kennel kennel3;

    private UserInfo userInfo;

    private Location location;

    private Breed breed;

    private PetType petType;

    private KennelDto kennelDto;

    private KennelNewDto kennelNewDto;

    private KennelUpdateDto kennelUpdateDto;

    private PageRequest pageRequest;

    @BeforeEach
    void beforeEach() {

        pageRequest = PageRequest.of(0 / 10, 10);

        petType = PetType.DOG;

        location = makeNewLocation(1);
        userInfo = makeNewUserInfo(1);

        breed = makeNewBreed();
        breed.setId(1L);

        kennel = makeNewKennel(1);
        kennel.setId(1L);
        kennel.setLocation(location);
        kennel.setBreed(breed);
        kennelDto = KennelMapper.returnKennelDto(kennel);

        kennel2 = makeNewKennel(2);
        kennel2.setId(2L);
        kennel2.setBreed(breed);
        kennel3 = makeNewKennel(3);
        kennel3.setId(3L);
        kennel3.setBreed(breed);

        user = makeNewUser(userInfo, kennel, 1);
        user.setId(1L);
        user.setKennel(kennel);
        Set<Role> roleSet = new HashSet<>();
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

        kennelUpdateDto = KennelUpdateDto.builder()
                .name("NameUp")
                .descriptions("DescriptionUp")
                .phone("+79001234569")
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

    @Test
    void updateKennel() {

        when(unionService.getUserOrNotFound(anyLong())).thenReturn(user);
        when(kennelRepository.save(any(Kennel.class))).thenReturn(kennel);

        KennelDto kennelDtoTest = kennelService.updateKennel(user.getId(),kennelUpdateDto);

        assertEquals(kennelDtoTest.getName(), kennel.getName());
        assertEquals(kennelDtoTest.getDescriptions(), kennel.getDescriptions());
        assertEquals(kennelDtoTest.getPhone(), kennel.getPhone());

        verify(kennelRepository, times(1)).save(kennel);
    }

    @Test
    void getPublicKennelById() {

        when(unionService.getKennelOrNotFound(anyLong())).thenReturn(kennel);

        KennelShortDto kennelShortDtoTest = kennelService.getPublicKennelById(kennel.getId());

        assertEquals(kennelShortDtoTest.getName(), kennelDto.getName());
        assertEquals(kennelShortDtoTest.getDescriptions(), kennelDto.getDescriptions());
        assertEquals(kennelShortDtoTest.getPhone(), kennelDto.getPhone());

        verify(unionService, times(1)).getKennelOrNotFound(kennel.getId());
    }

    @Test
    void getAllKennelByAdminFromParam() {

        List<Kennel> kennelList = List.of(kennel, kennel2, kennel3);

        when(unionService.makePetType(any(String.class))).thenReturn(PetType.DOG);
        when(kennelRepository.findKennelByParam(any(PetType.class), anyLong(), any())).thenReturn(kennelList);
        List<KennelDto> kennelDtoTestList = kennelService.getAllKennelByAdminFromParam(0, 10, "DOG", 1L);

        assertEquals(kennelList.size(), kennelDtoTestList.size());

        verify(kennelRepository, times(1)).findKennelByParam(PetType.DOG, 1L, pageRequest);
    }

    @Test
    void getAllKennelByPublicFromParam() {

        List<Kennel> kennelList = List.of(kennel, kennel2, kennel3);

        when(unionService.makePetType(any(String.class))).thenReturn(PetType.DOG);
        when(kennelRepository.findKennelByParam(any(PetType.class), anyLong(), any())).thenReturn(kennelList);
        List<KennelShortDto> kennelShortDtoTestList = kennelService.getAllKennelByPublicFromParam(0, 10, "DOG", 1L);

        assertEquals(kennelList.size(), kennelShortDtoTestList.size());

        verify(kennelRepository, times(1)).findKennelByParam(PetType.DOG, 1L, pageRequest);
    }
}
