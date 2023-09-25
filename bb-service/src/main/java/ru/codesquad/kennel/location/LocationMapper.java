package ru.codesquad.kennel.location;

import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface LocationMapper {

    Location returnLocation(LocationDto locationDto);
    LocationDto returnLocationDto(Location location);
}
