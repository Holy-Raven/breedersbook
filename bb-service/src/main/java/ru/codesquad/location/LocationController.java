package ru.codesquad.location;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static ru.codesquad.util.Constant.HEADER_USER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/private/location")
@Tag(name = "Private: адреса", description = "Закрытый API для работы с адресами")
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/user")
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "Добавление адреса для user",
            description = "Если пользователь не найден, возвращается статус NOT_FOUND и сообщение об ошибке."
    )
    public LocationDto addUserLocation(@RequestHeader(HEADER_USER) Long yourId,
                                       @Valid @RequestBody LocationDto locationDto) {

        log.info("User {} add location ", yourId);
        return locationService.addUserLocation(yourId, locationDto);
    }

    @PostMapping("/kennel")
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "Добавление адреса для kennel",
            description = "Если хозяин питомника не найден, возвращается статус NOT_FOUND и сообщение об ошибке. " +
                          "Если у пользователя нет питомника, возвращается статут CONFLICT и сообщение об ошибке."
    )
    public LocationDto addKennelLocation(@RequestHeader(HEADER_USER) Long yourId,
                                         @Valid @RequestBody LocationDto locationDto) {

        log.info("User {} add location in his kennel ", yourId);
        return locationService.addKennelLocation(yourId, locationDto);
    }

    @PostMapping("/kennel/default")
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "Добавление адреса для kennel по умолчанию (адрес пользователя)",
            description = "Если хозяин питомника не найден, возвращается статус NOT_FOUND и сообщение об ошибке. " +
                          "Если у пользователя нет питомника, возвращается статут CONFLICT и сообщение об ошибке." +
                          "Если у пользователя нет адреса, возвращается статут CONFLICT и сообщение об ошибке."
    )
    public LocationDto addKennelDefaultLocation(@RequestHeader(HEADER_USER) Long yourId) {

        log.info("User {} add location in his kennel ", yourId);
        return locationService.addKennelDefaultLocation(yourId);
    }

    @PatchMapping("/user")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Обновление адреса для user",
            description = "Если пользователь не найден, возвращается статус NOT_FOUND и сообщение об ошибке." +
                          "Если у пользователя нет адреса, возвращается статут CONFLICT и сообщение об ошибке."
    )
    public LocationDto updateUserLocation(@RequestHeader(HEADER_USER) Long yourId,
                                          @Valid @RequestBody LocationUpdateDto locationUpdateDto) {

        log.info("User id {} update location", yourId);
        return locationService.updateUserLocation(yourId, locationUpdateDto);
    }

    @PatchMapping("/kennel")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Обновление адреса для kennel",
            description = "Если хозяин питомника не найден, возвращается статус NOT_FOUND и сообщение об ошибке. " +
                          "Если у пользователя нет питомника, возвращается статут CONFLICT и сообщение об ошибке." +
                          "Если у питомника нет адреса, возвращается статут CONFLICT и сообщение об ошибке."
    )
    public LocationDto updateKennelLocation(@RequestHeader(HEADER_USER) Long yourId,
                                            @Valid @RequestBody LocationUpdateDto locationUpdateDto) {

        log.info("User id {} update kennel location", yourId);
        return locationService.updateKennelLocation(yourId, locationUpdateDto);
    }

    @DeleteMapping("/user")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление адреса у kennel",
            description = "Если хозяин питомника не найден, возвращается статус NOT_FOUND и сообщение об ошибке. " +
                          "Если у пользователя нет адреса, возвращается статут CONFLICT и сообщение об ошибке."
    )
    public void deleteUserLocation(@RequestHeader(HEADER_USER) Long yourId) {

        log.info("User {} deleted location from profile ", yourId);
        locationService.deleteUserLocation(yourId);
    }

    @DeleteMapping("/kennel")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление адреса у kennel",
            description = "Если хозяин питомника не найден, возвращается статус NOT_FOUND и сообщение об ошибке. " +
                          "Если у пользователя нет питомника, возвращается статут CONFLICT и сообщение об ошибке." +
                          "Если у питомника нет адреса, возвращается статут CONFLICT и сообщение об ошибке."
    )
    public void deleteKennelLocation(@RequestHeader(HEADER_USER) Long yourId) {

        log.info("User {} deleted location from his kennel ", yourId);
        locationService.deleteKennelLocation(yourId);
    }
}
