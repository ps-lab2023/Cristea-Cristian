package com.fitnessTracker.fitnessTrackerApp.util;
import java.util.UUID;

public class Utils {
    public static String generatePassword(int length) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid.substring(0, length);
    }
}
