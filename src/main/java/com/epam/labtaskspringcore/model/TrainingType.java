package com.epam.labtaskspringcore.model;

import java.util.Arrays;

public enum TrainingType {
    CARDIO("Cardio"),
    STRENGTH("Strength"),
    HIIT("High Intensity Interval Training"),
    YOGA("Yoga"),
    PILATES("Pilates"),
    GROUP("Group Class"),
    PERSONAL("Personal Training"),
    ;

    private final String displayName;

    TrainingType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static TrainingType fromDisplayName(String displayName) {
        return Arrays.stream(values())
                     .filter(type -> type.displayName.equalsIgnoreCase(displayName))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("Unknown training type: " + displayName));
    }
}
