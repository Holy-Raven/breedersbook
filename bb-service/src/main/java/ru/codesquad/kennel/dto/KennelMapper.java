package ru.codesquad.kennel.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.codesquad.kennel.Kennel;
import ru.codesquad.kennel.location.LocationMapper;

@Mapper(uses = {LocationMapper.class}, componentModel = "spring")
public interface KennelMapper {

    Kennel returnKennel(KennelDto kennelDto);

    @Mapping(target ="locationDto", source = "kennel.location")
    KennelDto returnKennelDto(Kennel kennel);

    @Mapping(target ="locationDto", source = "kennel.location")
    KennelShortDto returnKennelShortDto(Kennel kennel);
}
