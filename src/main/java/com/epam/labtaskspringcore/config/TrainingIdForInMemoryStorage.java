package com.epam.labtaskspringcore.config;

public class TrainingIdForInMemoryStorage {
    private static int userId = 3;
    public static int getNewId() {
        return userId++;
    }
}
