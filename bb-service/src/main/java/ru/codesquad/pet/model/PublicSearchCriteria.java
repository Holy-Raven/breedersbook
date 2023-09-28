package ru.codesquad.pet.model;

import lombok.Builder;
import lombok.Data;
import ru.codesquad.pet.enums.Color;
import ru.codesquad.pet.enums.PetSort;
import ru.codesquad.util.enums.Gender;
import ru.codesquad.util.enums.PetType;

import java.util.List;

@Data
@Builder
public class PublicSearchCriteria {
    PetType petType;
    List<Long> breedIds;
    String pattern;
    List<Color> colors;
    Gender gender;
    int priceFrom;
    Integer priceTo;
    PetSort petSort;
    int from;
    int size;
}