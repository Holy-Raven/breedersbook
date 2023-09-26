package ru.codesquad.kennel.dto;

import org.mapstruct.Mapper;
import ru.codesquad.kennel.Kennel;
import ru.codesquad.kennel.location.LocationMapper;

@Mapper(uses = {LocationMapper.class}, componentModel = "spring", disableSubMappingMethodsGeneration = true)
public interface KennelMapper {

    Kennel returnKennel(KennelDto kennelDto);

    KennelDto returnKennelDto(Kennel kennel);

    KennelShortDto returnKennelShortDto(Kennel kennel);
}
