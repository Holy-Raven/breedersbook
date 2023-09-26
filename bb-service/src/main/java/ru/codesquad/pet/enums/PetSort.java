package ru.codesquad.pet.enums;

import java.util.Optional;

public enum PetSort {
    PRICE_ASC,
    PRICE_DESC,
    SIZE,
    FUR,
    //DISTANCE,
    KENNEL_RATING;

    public static Optional<PetSort> from(String stringSort) {
        for (PetSort sort : values()) {
            if (sort.name().equalsIgnoreCase(stringSort)) {
                return java.util.Optional.of(sort);
            }
        }
        return java.util.Optional.empty();
    }

}
