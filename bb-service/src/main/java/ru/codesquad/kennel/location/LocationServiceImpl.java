package ru.codesquad.kennel.location;

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
public class LocationServiceImpl implements  LocationService {

    private final LocationRepository locationRepository;
    private final KennelRepository kennelRepository;
    private final UserRepository userRepository;
    private final UnionService unionService;
    private final LocationMapper locationMapper;

    @Override
    @Transactional
    public LocationDto addUserLocation(Long yourId, LocationDto locationDto) {

        User user = unionService.getUserOrNotFound(yourId);
        Location location = locationMapper.returnLocation(locationDto);
        location = locationRepository.save(location);

        user.setLocation(location);
        userRepository.save(user);

        return locationMapper.returnLocationDto(location);
    }

    @Override
    @Transactional
    public LocationDto addKennelLocation(Long yourId, LocationDto locationDto) {

        User user = unionService.getUserOrNotFound(yourId);

        Kennel kennel = user.getKennel();
        Location location = user.getLocation();

        if (kennel != null) {
            Location newLocation ;

            if (locationDto != null) {
                newLocation = locationMapper.returnLocation(locationDto);
                newLocation = locationRepository.save(newLocation);
                kennel.setLocation(newLocation);
                kennelRepository.save(kennel);
            }

        } else {
            throw new ConflictException("У юзера нет питомника");
        }

        return locationMapper.returnLocationDto(location);
    }

    @Override
    @Transactional
    public LocationDto addKennelDefaultLocation(Long yourId) {

        User user = unionService.getUserOrNotFound(yourId);

        Kennel kennel = user.getKennel();
        Location location = user.getLocation();

        if (kennel != null) {
            Location newLocation ;
            if (location != null) {
                newLocation = Location.builder()
                        .apartment(location.getApartment())
                        .city(location.getCity())
                        .country(location.getCountry())
                        .street(location.getStreet())
                        .house(location.getHouse())
                        .build();
                } else {
                    throw new ConflictException("Локация по умолчанию не была добавлена, у владельца питомника не заполнена локация");
                }
            newLocation = locationRepository.save(newLocation);
            kennel.setLocation(newLocation);
            kennelRepository.save(kennel);
        } else {
            throw new ConflictException("У юзера нет питомника");
        }

        return locationMapper.returnLocationDto(location);
    }

    @Override
    @Transactional
    public LocationDto updateUserLocation(Long yourId, LocationDto locationDto) {

        User user = unionService.getUserOrNotFound(yourId);
        Location location = user.getLocation();

        if (location == null) {
            throw new ConflictException("У юзера не заполнена локация");
        }
        location = locationRepository.save(updateLocation(location, locationDto));

        return locationMapper.returnLocationDto(location);
    }

    @Override
    @Transactional
    public LocationDto updateKennelLocation(Long yourId, LocationDto locationDto) {

        User user = unionService.getUserOrNotFound(yourId);
        Kennel kennel = user.getKennel();

        if (kennel != null) {
            Location location = kennel.getLocation();
            if (location != null) {
                location = locationRepository.save(updateLocation(location, locationDto));
                return locationMapper.returnLocationDto(location);
            } else {
                throw new ConflictException("У питомника не заполнена локация");
            }
        } else {
            throw new ConflictException("У юзера нет питомника");
        }
    }



    @Override
    @Transactional
    public Boolean deleteUserLocation(Long yourId) {

        User user = unionService.getUserOrNotFound(yourId);

        if (user.getLocation() == null) {
            throw new ConflictException("У юзера не заполнена локация");
        }

        Location location = unionService.getLocationOrNotFound(user.getLocation().getId());
        locationRepository.deleteById(location.getId());

        return true;
    }

    @Override
    @Transactional
    public Boolean deleteKennelLocation(Long yourId) {

        User user = unionService.getUserOrNotFound(yourId);
        Kennel kennel = user.getKennel();

        if (kennel != null) {
            Location location = kennel.getLocation();
            if (location != null) {
                unionService.getLocationOrNotFound(kennel.getLocation().getId());
                locationRepository.deleteById(location.getId());
            } else {
                throw new ConflictException("У питомника не заполнена локация");
            }
        } else {
            throw new ConflictException("У юзера нет питомника");
        }
        return true;
    }

    private Location updateLocation(Location location, LocationDto locationDto) {

            if (locationDto.getCountry() != null && !locationDto.getCountry().isBlank()) {
                location.setCountry(locationDto.getCountry());
            }
            if (locationDto.getCity() != null && !locationDto.getCity().isBlank()) {
                location.setCity(locationDto.getCity());
            }
            if (locationDto.getStreet() != null && !locationDto.getStreet().isBlank()) {
                location.setStreet(locationDto.getStreet());
            }
            if (locationDto.getHouse() != null && !locationDto.getHouse().isBlank()) {
                location.setHouse(locationDto.getHouse());
            }
            if (locationDto.getApartment() != null && locationDto.getApartment() > 0) {
                location.setApartment(locationDto.getApartment());
            }

        return location;
    }
}