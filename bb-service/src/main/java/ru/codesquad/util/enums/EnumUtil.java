package ru.codesquad.util.enums;

import ru.codesquad.exception.ValidationException;

public class EnumUtil {

    private EnumUtil() {
    }

    public static <T extends Enum<?>> T getValue(Class<T> enumType,
                                                 String name) {
        for (T value : enumType.getEnumConstants()) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        throw new ValidationException(String.format("Unknown %s: %s", Class.class.getSimpleName(), name));
    }
}