package ru.codesquad.util.enums;

import ru.codesquad.exception.ValidationException;

public enum Gender {

    MALE,
    FEMALE;

    public static Gender getGenderValue(String gender) {
        try {
            return Gender.valueOf(gender.toUpperCase());
        } catch (Exception e) {
            throw new ValidationException("Unknown Gender: " + gender);
        }
    }
}
