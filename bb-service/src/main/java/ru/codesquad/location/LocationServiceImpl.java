package ru.codesquad.location;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codesquad.exception.ConflictException;
import ru.codesquad.kennel.Kennel;
import ru.codesquad.kennel.KennelRepository;
import ru.codesquad.user.User;
import ru.codesquad.user.UserRepository;
import ru.codesquad.util.UnionService;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final KennelRepository kennelRepository;
    private final UserRepository userRepository;
    private final UnionService unionService;

    @Override
    @Transactional
    public LocationDto addUserLocation(Long yourId, LocationDto locationDto) {

        User user = unionService.getUserOrNotFound(yourId);
        Location location = LocationMapper.returnLocation(locationDto);

        location = locationRepository.save(location);

        user.setLocation(location);
        userRepository.save(user);

        return LocationMapper.returnLocationDto(location);
    }

    @Override
    @Transactional
    public LocationDto addKennelLocation(Long yourId, LocationDto locationDto) {

        User user = unionService.getUserOrNotFound(yourId);
        Kennel kennel = isUserKennel(user);

        if (locationDto != null) {
            Location location = LocationMapper.returnLocation(locationDto);
            location = locationRepository.save(location);
            kennel.setLocation(location);
            kennelRepository.save(kennel);
            return LocationMapper.returnLocationDto(location);
        } else {
            throw new ConflictException("Вы не указали локацию");
        }
    }

    @Override
    @Transactional
    public LocationDto addKennelDefaultLocation(Long yourId) {

        User user = unionService.getUserOrNotFound(yourId);
        Kennel kennel = isUserKennel(user);

        Location location = isUserLocation(user);

        Location newLocation = Location.builder()
                .apartment(location.getApartment())
                .city(location.getCity())
                .country(location.getCountry())
                .street(location.getStreet())
                .house(location.getHouse())
                .build();

        newLocation = locationRepository.save(newLocation);
        kennel.setLocation(newLocation);
        kennelRepository.save(kennel);
        return LocationMapper.returnLocationDto(location);
    }

    @Override
    @Transactional
    public LocationDto updateUserLocation(Long yourId, LocationUpdateDto locationUpdateDto) {

        User user = unionService.getUserOrNotFound(yourId);
        Location location = isUserLocation(user);

        location = locationRepository.save(updateLocation(location, locationUpdateDto));

        return LocationMapper.returnLocationDto(location);
    }

    @Override
    @Transactional
    public LocationDto updateKennelLocation(Long yourId, LocationUpdateDto locationUpdateDto) {

        User user = unionService.getUserOrNotFound(yourId);
        Kennel kennel = isUserKennel(user);
        Location location = isKennelLocation(kennel);

        location = locationRepository.save(updateLocation(location, locationUpdateDto));
        return LocationMapper.returnLocationDto(location);
    }

    @Override
    @Transactional
    public Boolean deleteUserLocation(Long yourId) {

        User user = unionService.getUserOrNotFound(yourId);
        Location location = isUserLocation(user);

        locationRepository.deleteById(location.getId());

        return true;
    }

    @Override
    @Transactional
    public Boolean deleteKennelLocation(Long yourId) {

        User user = unionService.getUserOrNotFound(yourId);
        Kennel kennel = isUserKennel(user);
//
//        isUserKennel(user);
        Location location = isKennelLocation(kennel);
//
//        Location location = kennel.getLocation();
//        isKennelLocation(kennel);

        unionService.getLocationOrNotFound(kennel.getLocation().getId());
        locationRepository.deleteById(location.getId());

        return true;
    }

    private Location updateLocation(Location location, LocationUpdateDto locationUpdateDto) {

        if (locationUpdateDto.getCountry() != null && !locationUpdateDto.getCountry().isBlank()) {
            location.setCountry(locationUpdateDto.getCountry());
        }
        if (locationUpdateDto.getCity() != null && !locationUpdateDto.getCity().isBlank()) {
            location.setCity(locationUpdateDto.getCity());
        }
        if (locationUpdateDto.getStreet() != null && !locationUpdateDto.getStreet().isBlank()) {
            location.setStreet(locationUpdateDto.getStreet());
        }
        if (locationUpdateDto.getHouse() != null && !locationUpdateDto.getHouse().isBlank()) {
            location.setHouse(locationUpdateDto.getHouse());
        }
        if (locationUpdateDto.getApartment() != null && locationUpdateDto.getApartment() > 0) {
            location.setApartment(locationUpdateDto.getApartment());
        }

        return location;
    }

    private Kennel isUserKennel(User user) {

        if (user.getKennel() == null) {
            throw new ConflictException("У юзера нет питомника");
        } else {
            return user.getKennel();
        }
    }

    private Location isKennelLocation(Kennel kennel) {
        if (kennel.getLocation() == null) {
            throw new ConflictException("У питомника не заполнена локация");
        } else {
            return kennel.getLocation();
        }
    }

    private Location isUserLocation(User user) {
        if (user.getLocation() == null) {
            throw new ConflictException("У user не заполнена локация");
        } else {
            return user.getLocation();
        }
    }
}
