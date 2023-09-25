package ru.codesquad.pet.enums;

import java.util.Optional;

public enum DogPattern {
    SPOTTED,
    BLACK_WHITE,
    MERLE,
    BRINDLE,
    TRICOLOR,
    BLACK_TAN,
    SABLE,
    HARLEQUIN,
    PIEBALD,
    TUXEDO,
    FLECKED,
    SADDLE;

    public static Optional<DogPattern> from(String stringType) {
        for (DogPattern pattern : values()) {
            if (pattern.name().equalsIgnoreCase(stringType)) {
                return java.util.Optional.of(pattern);
            }
        }
        return java.util.Optional.empty();
    }
}
