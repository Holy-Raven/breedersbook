package ru.codesquad.pet.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.codesquad.breed.enums.FurType;
import ru.codesquad.pet.dto.PetShortDto;
import ru.codesquad.pet.enums.CatPattern;
import ru.codesquad.pet.enums.Color;
import ru.codesquad.pet.enums.DogPattern;
import ru.codesquad.pet.enums.PetSort;
import ru.codesquad.pet.service.PetService;
import ru.codesquad.util.enums.EnumUtil;
import ru.codesquad.util.enums.PetType;

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

        PetType petType = EnumUtil.getValue(PetType.class, petTypeParam);
        FurType fur = furParam == null ? null : EnumUtil.getValue(FurType.class, furParam);
        DogPattern dogPattern = patternParam == null || petType == PetType.CAT ? null :
                EnumUtil.getValue(DogPattern.class, patternParam);
        CatPattern catPattern = patternParam == null || petType == PetType.DOG ? null :
                EnumUtil.getValue(CatPattern.class, patternParam);
        List<Color> colors = colorParams == null ? null : colorParams.stream()
                .map(colorParam -> EnumUtil.getValue(Color.class, colorParam))
                .collect(Collectors.toList());
        PetSort sort = sortParam == null ? null : EnumUtil.getValue(PetSort.class, sortParam);

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
