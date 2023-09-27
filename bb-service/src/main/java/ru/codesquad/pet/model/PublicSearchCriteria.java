package ru.codesquad.pet.model;

import lombok.Builder;
import lombok.Data;
import ru.codesquad.pet.enums.PetSort;
import ru.codesquad.pet.enums.SaleStatus;
import ru.codesquad.util.enums.Gender;

@Data
@Builder
public class PublicSearchCriteria {
    long userId;
    Gender gender;
    SaleStatus saleStatus;
    PetSort petSort;
    int from;
    int size;
}
