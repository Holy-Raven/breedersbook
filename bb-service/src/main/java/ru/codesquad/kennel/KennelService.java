package ru.codesquad.kennel;

import ru.codesquad.kennel.dto.KennelDto;
import ru.codesquad.kennel.dto.KennelNewDto;
import ru.codesquad.kennel.dto.KennelShortDto;
import ru.codesquad.kennel.dto.KennelUpdateDto;
import ru.codesquad.util.enums.PetType;

import java.util.List;

public interface KennelService {

    KennelDto getPrivateKennelById(Long kennelId, Long yourId);

    Boolean deleteKennel(Long yourId);

    Boolean deletePrivateKennel(Long yourId);

    KennelDto updateKennel(Long yourId, KennelUpdateDto kennelUpdateDto);

    KennelDto addKennel(Long yourId, KennelNewDto kennelNewDto);

    KennelShortDto getPublicKennelById(Long kennelId);

    List<KennelDto> getAllKennelByAdminFromParam(Integer from, Integer size, String type, Long breedId);

    List<KennelShortDto> getAllKennelByPublicFromParam(Integer from, Integer size, String type, Long breedId);
}
