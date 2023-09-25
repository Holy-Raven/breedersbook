package ru.codesquad.pet.enums;

import java.util.Optional;

public enum FurType {
    LONG,
    SHORT,
    CURLY,
    HAIRLESS;

    public static Optional<FurType> from(String stringType) {
        for (FurType type : values()) {
            if (type.name().equalsIgnoreCase(stringType)) {
                return java.util.Optional.of(type);
            }
        }
        return java.util.Optional.empty();
    }
}
