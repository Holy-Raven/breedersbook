package ru.codesquad.pet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.breed.enums.FurType;
import ru.codesquad.pet.dto.PetShortDto;
import ru.codesquad.pet.enums.*;
import ru.codesquad.pet.service.PetService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

import static ru.codesquad.exception.util.ErrorMessages.FROM_ERROR_MESSAGE;
import static ru.codesquad.exception.util.ErrorMessages.SIZE_ERROR_MESSAGE;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/pets")
public class PublicPetController {
    private final PetService service;

    @GetMapping
    public List<PetShortDto> getByFiltersPublic(@RequestParam String petTypeParam,
                                                @RequestParam(required = false) Long breedId,
                                                @RequestParam(required = false) String furParam,
                                                @RequestParam(required = false) String patternParam,
                                                @RequestParam(required = false) List<String> colorParams,
                                                @PositiveOrZero
                                                @RequestParam(defaultValue = "0") int priceFrom,
                                                @Positive
                                                @RequestParam(required = false) Integer priceTo,
                                                @RequestParam(required = false, name = "sort") String sortParam,
                                                @PositiveOrZero(message = FROM_ERROR_MESSAGE)
                                                @RequestParam(defaultValue = "0") Integer from,
                                                @Positive(message = SIZE_ERROR_MESSAGE)
                                                @RequestParam(defaultValue = "10") Integer size,
                                                HttpServletRequest request) {

        PetType petType = PetType.from(petTypeParam)
                .orElseThrow(() -> new IllegalArgumentException("Unknown pet type: " + petTypeParam));
        FurType fur = furParam == null ? null : FurType.from(furParam)
                .orElseThrow(() -> new IllegalArgumentException("Unknown fur type: " + sortParam));
        DogPattern dogPattern = patternParam == null || petType == PetType.CAT ? null :
                DogPattern.from(patternParam).orElseThrow(() -> new IllegalArgumentException("Unknown pattern: " + patternParam));
        CatPattern catPattern = patternParam == null || petType == PetType.DOG ? null :
                CatPattern.from(patternParam).orElseThrow(() -> new IllegalArgumentException("Unknown pattern: " + patternParam));
        List<Color> colors = colorParams == null ? null : colorParams.stream()
                .map(colorParam -> Color.from(colorParam)
                        .orElseThrow(() -> new IllegalArgumentException("Unknown fur type: " + colorParam)))
                .collect(Collectors.toList());
        PetSort sort = sortParam == null ? null : PetSort.from(sortParam)
                .orElseThrow(() -> new IllegalArgumentException("Unknown state: " + sortParam));

        String ip = request.getRemoteAddr();
        return service.getByFiltersPublic(petType, breedId, fur, catPattern, dogPattern, colors,
                priceFrom, priceTo,
                sort,
                from, size,
                ip);
    }

    @GetMapping("/{petId}")
    public PetShortDto getByIdPublic(@PathVariable long petId,
                                     HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        return service.getByIdPublic(petId, ip);
    }
}
