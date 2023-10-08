package ru.codesquad.pet.model;

import lombok.Builder;
import lombok.Data;
import ru.codesquad.pet.enums.PetSort;
import ru.codesquad.pet.enums.SaleStatus;
import ru.codesquad.user.User;
import ru.codesquad.util.enums.Gender;

@Data
@Builder
public class PrivateSearchCriteria {
    User owner;
    Gender gender;
    SaleStatus saleStatus;
    PetSort sort;
    int from;
    int size;
}
