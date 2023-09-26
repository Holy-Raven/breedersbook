package ru.codesquad.util.enums;

import ru.codesquad.exception.ValidationException;

import java.util.Optional;

public enum PetType {
    CAT,
    DOG;

    public static PetType getValue(String type) {
        try {
            return PetType.valueOf(type.toUpperCase());
        } catch (Exception e) {
            throw new ValidationException("Unknown Pet type: " + type);
        }
    }
}
