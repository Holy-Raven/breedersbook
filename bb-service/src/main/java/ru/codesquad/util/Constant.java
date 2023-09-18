package ru.codesquad.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Constant {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final LocalDateTime CURRENT_TIME = LocalDateTime.now();
}
