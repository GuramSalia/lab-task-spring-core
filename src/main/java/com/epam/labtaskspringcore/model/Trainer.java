package com.epam.labtaskspringcore.model;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Trainer extends User {

    private int trainerId;
    private TrainingType specialization;

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
