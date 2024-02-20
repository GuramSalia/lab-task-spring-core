package com.epam.labtaskspringcore.model;

import java.time.Duration;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Training {
    private int trainingId;

    private int traineeId;

    private int trainerId;

    private String name;

    private TrainingType type;

    private Date date;

    private Duration durationInMinutes;
}
