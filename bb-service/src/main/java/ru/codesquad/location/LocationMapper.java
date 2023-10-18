package ru.codesquad.location;

public class LocationMapper {
    public static Location returnLocation(LocationDto locationDto) {
        return Location.builder()
                .country(locationDto.getCountry())
                .city(locationDto.getCity())
                .street(locationDto.getStreet())
                .house(locationDto.getHouse())
                .apartment(locationDto.getApartment())
                .build();
    }

    public static LocationDto returnLocationDto(Location location) {
        return LocationDto.builder()
                .country(location.getCountry())
                .city(location.getCity())
                .street(location.getStreet())
                .house(location.getHouse())
                .apartment(location.getApartment())
                .build();
    }
}
