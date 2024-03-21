package com.epam.labtaskspringcore.config;

public class UserIdForInMemoryStorage {
    private static int userId = 5;
    public static int getNewId() {return userId++;}
}
