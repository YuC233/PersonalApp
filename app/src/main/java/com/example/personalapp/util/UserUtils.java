package com.example.personalapp.util;

public class UserUtils {
    private static int userId;

    public synchronized static void setUserId(int id) {
        userId = id;
    }

    public static int getUserId() {
        return userId;
    }
}
