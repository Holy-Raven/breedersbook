package ru.codesquad.kennel;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codesquad.exception.ConflictException;
import ru.codesquad.kennel.dto.*;
import ru.codesquad.user.User;
import ru.codesquad.user.UserRepository;
import ru.codesquad.util.UnionService;
import java.util.ArrayList;
import java.util.List;
import static ru.codesquad.util.Constant.CURRENT_TIME;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class KennelServiceImpl implements KennelService {

    private final UnionService unionService;
    private final KennelMapper kennelMapper;
    private final KennelRepository kennelRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public KennelDto addKennel(Long yourId, KennelNewDto kennelNewDto) {

        User user = unionService.getUserOrNotFound(yourId);
        Kennel kennel = kennelMapper.returnKennel(kennelNewDto);

        kennel.setPhone(unionService.checkPhoneNumber(kennel.getPhone()));
        kennel = kennelRepository.save(kennel);
        user.setKennel(kennel);
        userRepository.save(user);

        return kennelMapper.returnKennelDto(kennel);
    }

    @Override
    @Transactional(readOnly = true)
    public KennelDto getPrivateKennelById(Long kennelId, Long yourId) {

        User user = unionService.getUserOrNotFound(yourId);
        Kennel kennel = unionService.getKennelOrNotFound(kennelId);

        if (user.getKennel().equals(kennel)) {
            return kennelMapper.returnKennelDto(kennel);
        } else {
            throw new ConflictException("Этот питомник принадлежит другому юзепру");
        }
    }

    @Override
    @Transactional
    public Boolean deleteKennel(Long kennelId) {

        unionService.getKennelOrNotFound(kennelId);
        kennelRepository.deleteById(kennelId);

        return true;
    }

    @Override
    @Transactional
    public Boolean deletePrivateKennel(Long yourId) {

        User user = unionService.getUserOrNotFound(yourId);
        Kennel kennel = user.getKennel();

        if (kennel != null) {
            kennelRepository.deleteById(kennel.getId());
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

            return kennelMapper.returnKennelDto(kennel);
        }  else {
            throw new ConflictException("У юзера нет питомника");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public KennelShortDto getPublicKennelById(Long kennelId) {

        Kennel kennel = unionService.getKennelOrNotFound(kennelId);

        return kennelMapper.returnKennelShortDto(kennel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<KennelDto> getAdminAllKennels(Integer from, Integer size) {

        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<KennelDto> kennelDtoList = new ArrayList<>();

        kennelRepository.findAll(pageRequest).forEach(kennel -> kennelDtoList.add(kennelMapper.returnKennelDto(kennel)));

        return kennelDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<KennelShortDto> getPublicAllKennels(Integer from, Integer size) {

        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<KennelShortDto> kennelShortDto = new ArrayList<>();

        kennelRepository.findAll(pageRequest).forEach(kennel -> kennelShortDto.add(kennelMapper.returnKennelShortDto(kennel)));

        return kennelShortDto;
    }
}
