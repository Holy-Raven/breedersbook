package ru.codesquad.pet.enums;

import java.util.Optional;

public enum Color {
    WHITE,
    BLACK,
    RED,
    BLUE,
    GREY,
    CREAM,
    BROWN,
    CINNAMON,
    FAWN,
    GOLDEN;

    public static Optional<Color> from(String stringColor) {
        for (Color color : values()) {
            if (color.name().equalsIgnoreCase(stringColor)) {
                return java.util.Optional.of(color);
            }
        }
        return java.util.Optional.empty();
    }
}
