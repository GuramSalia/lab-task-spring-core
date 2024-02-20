package com.epam.labtaskspringcore.model;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;

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
    private int durationInMinutes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        return getTrainingId() == training.getTrainingId() && getTraineeId() == training.getTraineeId() && getTrainerId() == training.getTrainerId() && getDurationInMinutes() == training.getDurationInMinutes() && Objects.equals(
                getName(),
                training.getName()) && getType() == training.getType() && Objects.equals(getDate(),
                                                                                         training.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTrainingId(),
                            getTraineeId(),
                            getTrainerId(),
                            getName(),
                            getType(),
                            getDate(),
                            getDurationInMinutes());
    }

    @Override
    public String toString() {
        return "Training{" +
                "\n  id=" + trainingId +
                ", \n  traineeId=" + traineeId +
                ", \n  trainerId=" + trainerId +
                ", \n  name='" + name + '\'' +
                ", \n  type=" + type +
                ", \n  date=" + date +
                ", \n  durationInMinutes=" + durationInMinutes +
                '}';
    }
}
