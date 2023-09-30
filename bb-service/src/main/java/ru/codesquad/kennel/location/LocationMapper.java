package ru.codesquad.kennel.location;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", disableSubMappingMethodsGeneration = true)
public interface LocationMapper {

    Location returnLocation(LocationDto locationDto);
    LocationDto returnLocationDto(Location location);
}
