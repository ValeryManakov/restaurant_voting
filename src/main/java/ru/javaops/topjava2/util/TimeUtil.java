package ru.javaops.topjava2.util;

import lombok.experimental.UtilityClass;

import java.time.LocalTime;

@UtilityClass
public class TimeUtil {

    public static boolean isTimeCorrect(LocalTime time) {
        return time.isBefore(LocalTime.of(11, 0));
    }
}