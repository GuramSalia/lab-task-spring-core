package com.epam.labtaskspringcore.model;

import java.time.Duration;
import java.util.Date;

public class Training {
    private long trainingId;
    private long traineeId;
    private long trainerId;
    private String name;

    // !!!! here I need to clarify what Training type is, most probably it is Enum
    private TrainingType type;
    private Date date;
    private Duration durationInMinutes;

    public long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(long trainingId) {
        this.trainingId = trainingId;
    }

    public long getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(long traineeId) {
        this.traineeId = traineeId;
    }

    public long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(long trainerId) {
        this.trainerId = trainerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TrainingType getType() {
        return type;
    }

    public void setType(TrainingType type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Duration getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(Duration durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }
}
