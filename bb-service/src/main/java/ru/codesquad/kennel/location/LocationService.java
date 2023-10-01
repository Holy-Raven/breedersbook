package ru.codesquad.kennel.location;

public interface LocationService {

    LocationDto addUserLocation(Long yourId, LocationDto locationDto);

    LocationDto addKennelLocation(Long yourId, LocationDto locationDto);

    LocationDto addKennelDefaultLocation(Long yourId);

    LocationDto updateUserLocation(Long yourId, LocationDto locationDto);

    LocationDto updateKennelLocation(Long yourId, LocationDto locationDto);

    Boolean deleteUserLocation(Long yourId);

    Boolean deleteKennelLocation(Long yourId);
}
