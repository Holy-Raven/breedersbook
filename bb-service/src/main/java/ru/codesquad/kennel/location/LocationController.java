package ru.codesquad.kennel.location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import static ru.codesquad.util.Constant.HEADER_USER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/location")
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/user")
    @ResponseStatus(value = HttpStatus.CREATED)
    public LocationDto addUserLocation(@RequestHeader(HEADER_USER) Long yourId,
                                       @Valid @RequestBody LocationDto locationDto) {

        log.info("User {} add location ", yourId);
        return locationService.addUserLocation(yourId, locationDto);
    }

    @PostMapping("/kennel")
    @ResponseStatus(value = HttpStatus.CREATED)
    public LocationDto addKennelLocation(@RequestHeader(HEADER_USER) Long yourId,
                                         @Valid @RequestBody LocationDto locationDto) {

        log.info("User {} add location in his kennel ", yourId);
        return locationService.addKennelLocation(yourId, locationDto);
    }

    @PostMapping("/kennel/default")
    @ResponseStatus(value = HttpStatus.CREATED)
    public LocationDto addKennelDefaultLocation(@RequestHeader(HEADER_USER) Long yourId) {

        log.info("User {} add location in his kennel ", yourId);
        return locationService.addKennelDefaultLocation(yourId);
    }

    @PatchMapping("/user")
    @ResponseStatus(value = HttpStatus.OK)
    public LocationDto updateUserLocation(@RequestHeader(HEADER_USER) Long yourId,
                                          @Valid @RequestBody LocationDto locationDto) {

        log.info("User id {} update location", yourId);
        return locationService.updateUserLocation(yourId, locationDto);
    }

    @PatchMapping("/kennel")
    @ResponseStatus(value = HttpStatus.OK)
    public LocationDto updateKennelLocation(@RequestHeader(HEADER_USER) Long yourId,
                                            @Valid @RequestBody LocationDto locationDto) {

        log.info("User id {} update kennel location", yourId);
        return locationService.updateKennelLocation(yourId, locationDto);
    }

    @DeleteMapping("/user")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUserLocation(@RequestHeader(HEADER_USER) Long yourId) {

        log.info("User {} deleted location from profile ", yourId);
        locationService.deleteUserLocation(yourId);
    }

    @DeleteMapping("/kennel")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteKennelLocation(@RequestHeader(HEADER_USER) Long yourId) {

        log.info("User {} deleted location from his kennel ", yourId);
        locationService.deleteKennelLocation(yourId);
    }
}
