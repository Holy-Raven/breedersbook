package ru.codesquad.pet.enums;

import java.util.Optional;

public enum SaleStatus {
    FOR_SALE,
    BOOKED,
    SOLD,
    NOT_FOR_SALE;

    public static Optional<SaleStatus> from(String stringStatus) {
        for (SaleStatus status : values()) {
            if (status.name().equalsIgnoreCase(stringStatus)) {
                return java.util.Optional.of(status);
            }
        }
        return java.util.Optional.empty();
    }
}
