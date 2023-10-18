package ru.codesquad.location;

public interface LocationService {

    LocationDto addUserLocation(Long yourId, LocationDto locationDto);

    LocationDto addKennelLocation(Long yourId, LocationDto locationDto);

    LocationDto addKennelDefaultLocation(Long yourId);

    LocationDto updateUserLocation(Long yourId, LocationUpdateDto locationUpdateDto);

    LocationDto updateKennelLocation(Long yourId, LocationUpdateDto locationUpdateDto);

    Boolean deleteUserLocation(Long yourId);

    Boolean deleteKennelLocation(Long yourId);
}
