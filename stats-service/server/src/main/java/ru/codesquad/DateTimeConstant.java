package ru.codesquad;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateTimeConstant {
    public final String dtPattern = "yyyy-MM-dd HH:mm:ss";
    public final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern(dtPattern);

}
