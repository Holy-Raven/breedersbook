package ru.codesquad.kennel;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codesquad.breed.Breed;
import ru.codesquad.exception.ConflictException;
import ru.codesquad.kennel.dto.*;
import ru.codesquad.role.Role;
import ru.codesquad.role.RoleService;
import ru.codesquad.user.User;
import ru.codesquad.user.UserRepository;
import ru.codesquad.util.UnionService;
import ru.codesquad.util.enums.EnumUtil;
import ru.codesquad.util.enums.PetType;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.codesquad.kennel.dto.KennelMapper.*;
import static ru.codesquad.util.Constant.CURRENT_TIME;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class KennelServiceImpl implements KennelService {

    private final KennelRepository kennelRepository;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UnionService unionService;

    @Override
    @Transactional
    public KennelDto addKennel(Long yourId, KennelNewDto kennelNewDto) {

        User user = unionService.getUserOrNotFound(yourId);
        Breed breed = unionService.getBreedOrNotFound(kennelNewDto.getBreedId());
        Kennel kennel = returnKennel(kennelNewDto, breed);

        kennel.setPhone(unionService.checkPhoneNumber(kennel.getPhone()));
        kennel = kennelRepository.save(kennel);
        user.setKennel(kennel);

        Set<Role> roles = user.getRoles();
        roles.add(roleService.getBreederRole());
        user.setRoles(roles);

        userRepository.save(user);

        return returnKennelDto(kennel);
    }

    @Override
    @Transactional(readOnly = true)
    public KennelDto getPrivateKennelById(Long kennelId, Long yourId) {

        User user = unionService.getUserOrNotFound(yourId);
        Kennel kennel = unionService.getKennelOrNotFound(kennelId);

        if (user.getKennel().equals(kennel)) {
            return returnKennelDto(kennel);
        } else {
            throw new ConflictException("Этот питомник принадлежит другому юзепру");
        }
    }

    @Override
    @Transactional
    public Boolean deleteKennel(Long kennelId) {

        unionService.getKennelOrNotFound(kennelId);
        User user = userRepository.findByKennelId(kennelId);
        kennelRepository.deleteById(kennelId);

        Set<Role> roles = user.getRoles();
        roles.remove(roleService.getBreederRole());
        user.setRoles(roles);
        userRepository.save(user);

        return true;
    }

    @Override
    @Transactional
    public Boolean deletePrivateKennel(Long yourId) {

        User user = unionService.getUserOrNotFound(yourId);
        Kennel kennel = user.getKennel();

        if (kennel != null) {
            kennelRepository.deleteById(kennel.getId());

            Set<Role> roles = user.getRoles();
            roles.remove(roleService.getBreederRole());
            user.setRoles(roles);
            userRepository.save(user);

            return true;
        } else {
            throw new ConflictException("У юзера нет притомника");
        }
    }

    @Override
    @Transactional
    public KennelDto updateKennel(Long yourId, KennelUpdateDto kennelUpdateDto) {

        User user = unionService.getUserOrNotFound(yourId);
        Kennel kennel = user.getKennel();

        if (kennel != null) {
            if (kennelUpdateDto.getName() != null && !kennelUpdateDto.getName().isBlank()) {
                kennel.setName(kennelUpdateDto.getName());
            }
            if (kennelUpdateDto.getDescriptions() != null && !kennelUpdateDto.getDescriptions().isBlank()) {
                kennel.setDescriptions(kennelUpdateDto.getDescriptions());
            }
            if (kennelUpdateDto.getPhone() != null && !kennelUpdateDto.getPhone().isBlank()) {
                kennel.setPhone(unionService.checkPhoneNumber(kennelUpdateDto.getPhone()));
            }
            if (kennelUpdateDto.getPhoto() != null && !kennelUpdateDto.getPhoto().isBlank()) {
                kennel.setPhoto(kennelUpdateDto.getPhoto());
            }
            if (kennelUpdateDto.getCreated() != null && !kennelUpdateDto.getCreated().isBefore(CURRENT_TIME)) {
                kennel.setPhoto(kennelUpdateDto.getPhoto());
            }

            kennel = kennelRepository.save(kennel);

            return returnKennelDto(kennel);
        } else {
            throw new ConflictException("У юзера нет питомника");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public KennelShortDto getPublicKennelById(Long kennelId) {

        Kennel kennel = unionService.getKennelOrNotFound(kennelId);

        return returnKennelShortDto(kennel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<KennelDto> getAllKennelByAdminFromParam(Integer from, Integer size, String type, Long breedId) {

        PetType petType = null;
        if (type !=null) {
            petType = EnumUtil.getValue(PetType.class, type);
        }

        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Kennel> kennelList = kennelRepository.findKennelByParam(petType, breedId, pageRequest);

        return kennelList.stream().map(KennelMapper::returnKennelDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<KennelShortDto> getAllKennelByPublicFromParam(Integer from, Integer size, String type, Long breedId) {

        PetType petType = null;
        if (type !=null) {
            petType = EnumUtil.getValue(PetType.class, type);
        }

        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Kennel> kennelList = kennelRepository.findKennelByParam(petType, breedId, pageRequest);

        return  kennelList.stream().map(KennelMapper::returnKennelShortDto).collect(Collectors.toList());
    }
}
