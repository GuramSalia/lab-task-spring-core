package com.epam.labtaskspringcore.model;

import java.util.Objects;

public class Trainer extends User {

    private long trainerId;
    private TrainingType specialization;

    public long getTrainerId() {return trainerId;}

    public void setTrainerId(long trainerId) {this.trainerId = trainerId;}

    public TrainingType getSpecialization() {return specialization;}

    public void setSpecialization(TrainingType specialization) {this.specialization = specialization;}

    @Override
    public String toString() {
        return "Trainer{" +
                "trainerId=" + trainerId +
                ", first-name='" + getFirstName() + '\'' +
                ", last-name='" + getLastName() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainer trainer = (Trainer) o;
        return getTrainerId() == trainer.getTrainerId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTrainerId(), getFirstName(), getLastName());
    }
}
