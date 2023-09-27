package ru.codesquad.pet.model;

import lombok.Builder;
import lombok.Data;
import ru.codesquad.pet.enums.PetSort;
import ru.codesquad.pet.enums.SaleStatus;
import ru.codesquad.util.enums.Gender;

@Data
@Builder
public class PrivateSearchCriteria {
    long userId;
    Gender gender;
    SaleStatus saleStatus;
    PetSort sort;
    int from;
    int size;
}
