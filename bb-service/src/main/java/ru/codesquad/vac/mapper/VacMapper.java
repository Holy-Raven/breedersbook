package ru.codesquad.vac.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.codesquad.vac.Vac;
import ru.codesquad.vac.VacPetShortDto;

@Mapper
public interface VacMapper {
    VacMapper INSTANCE = Mappers.getMapper(VacMapper.class);
    VacPetShortDto toPetShortDto(Vac vac);
}
