package ru.codesquad.kennel.dto;

import org.mapstruct.Mapper;
import ru.codesquad.kennel.Kennel;

@Mapper(componentModel = "spring")
public interface KennelMapper {

    Kennel returnKennel(KennelDto kennelDto);

    KennelDto returnKennelDto(Kennel kennel);
}
