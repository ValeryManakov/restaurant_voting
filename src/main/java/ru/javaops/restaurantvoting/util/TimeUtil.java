package ru.javaops.restaurantvoting.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalTime;

@UtilityClass
public class TimeUtil {
    public static final LocalTime DEAD_LINE = LocalTime.of(11, 0);
    public static final LocalDate TODAY = LocalDate.now();

    public static boolean isTimeCorrect(LocalTime time) {
        return time.isBefore(DEAD_LINE);
    }
}