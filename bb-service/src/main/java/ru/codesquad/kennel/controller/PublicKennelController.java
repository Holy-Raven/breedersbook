package ru.codesquad.kennel.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.kennel.KennelService;
import ru.codesquad.kennel.dto.KennelDto;
import ru.codesquad.kennel.dto.KennelNewDto;
import ru.codesquad.kennel.dto.KennelShortDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/kennels")
public class PublicKennelController {

    private final KennelService kennelService;

    @GetMapping("/{kennelId}")
    @ResponseStatus(value = HttpStatus.OK)
    public KennelShortDto getKennel(@PathVariable Long kennelId) {

        log.info("Get kennel {} ", kennelId);
        return kennelService.getPublicKennelById(kennelId);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<KennelShortDto> getAllKennels(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                              @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        log.info("List all Kennels");
        return kennelService.getPublicAllKennels(from, size);
    }
}
