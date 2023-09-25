package ru.codesquad.pet.enums;

import java.util.Optional;

public enum PetType {
    CAT,
    DOG;

    public static Optional<PetType> from(String stringType) {
        for (PetType type : values()) {
            if (type.name().equalsIgnoreCase(stringType)) {
                return java.util.Optional.of(type);
            }
        }
        return java.util.Optional.empty();
    }
}
