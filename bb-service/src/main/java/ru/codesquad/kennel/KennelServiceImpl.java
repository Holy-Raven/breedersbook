package ru.codesquad.kennel;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codesquad.kennel.dto.KennelDto;
import ru.codesquad.kennel.dto.KennelNewDto;
import ru.codesquad.kennel.dto.KennelShortDto;
import ru.codesquad.kennel.dto.KennelUpdateDto;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class KennelServiceImpl implements KennelService {

    @Override
    public KennelDto getPrivateKennelById(Long kennelId, Long yourId) {
        return null;
    }

    @Override
    public Boolean deleteKennel(Long yourId) {
        return null;
    }

    @Override
    public KennelDto updateKennel(Long kennelId, Long yourId, KennelUpdateDto kennelUpdateDto) {
        return null;
    }

    @Override
    public KennelDto addKennel(Long yourId, KennelNewDto kennelNewDto) {
        return null;
    }

    @Override
    public KennelShortDto getPublicKennelById(Long kennelId) {
        return null;
    }

    @Override
    public List<KennelDto> getAdminAllKennels(Integer from, Integer size) {
        return null;
    }

    @Override
    public List<KennelShortDto> getPublicAllKennels(Integer from, Integer size) {
        return null;
    }
}
