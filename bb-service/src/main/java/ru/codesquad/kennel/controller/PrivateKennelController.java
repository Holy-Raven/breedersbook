package ru.codesquad.kennel.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.kennel.KennelService;
import ru.codesquad.kennel.dto.KennelDto;
import ru.codesquad.kennel.dto.KennelNewDto;
import ru.codesquad.kennel.dto.KennelUpdateDto;

import javax.validation.Valid;

import static ru.codesquad.util.Constant.HEADER_USER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/private/kennels")
public class PrivateKennelController {

    private final KennelService kennelService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public KennelDto addKennel(@RequestHeader(HEADER_USER) Long yourId,
                               @Valid @RequestBody KennelNewDto kennelNewDto) {

        log.info("User {} add kennel {} ", yourId, kennelNewDto.getName());
        return kennelService.addKennel(yourId, kennelNewDto);
    }

    @GetMapping("/{kennelId}")
    @ResponseStatus(value = HttpStatus.OK)
    public KennelDto getKennel(@RequestHeader(HEADER_USER) Long yourId,
                               @PathVariable Long kennelId) {

        log.info("Get Kennel {} ", kennelId);
        return kennelService.getPrivateKennelById(kennelId,yourId);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteKennel(@RequestHeader(HEADER_USER) Long yourId) {

        log.info("User {} deleted his kennel ", yourId);
        kennelService.deleteKennel(yourId);
    }

    @PatchMapping("/{kennelId}")
    @ResponseStatus(value = HttpStatus.OK)
    public KennelDto updateKennel(@RequestHeader(HEADER_USER) Long yourId,
                                 @Valid @RequestBody KennelUpdateDto kennelUpdateDto,
                                 @PathVariable Long kennelId) {

        log.info("User id {} update profile his kennel", kennelId);
        return kennelService.updateKennel(kennelId, yourId, kennelUpdateDto);
    }
}
