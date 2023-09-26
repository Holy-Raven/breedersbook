package ru.codesquad.pet.enums;

import java.util.Optional;

public enum CatPattern {
    SOLID,
    BI_COLOUR,
    TABBY,
    TRI_COLOR,
    TORTOISESHELL,
    COLOUR_POINT;

    public static Optional<CatPattern> from(String stringType) {
        for (CatPattern pattern : values()) {
            if (pattern.name().equalsIgnoreCase(stringType)) {
                return java.util.Optional.of(pattern);
            }
        }
        return java.util.Optional.empty();
    }
}