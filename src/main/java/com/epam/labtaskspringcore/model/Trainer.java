package com.epam.labtaskspringcore.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class Trainer extends User {

    private int trainerId;
    private TrainingType specialization;

    @Override
    public String toString() {
        return "Trainer{" +
                "\n  id=" + trainerId +
                ", \n  name='" + getFirstName() + ' ' + getLastName() + '\'' +
                ", \n  username='" + getUsername() + '\'' +
                ", \n  password='" + getPassword() + '\'' +
                ", \n  spec.='" + getSpecialization() + '\'' +
                ", \n  isActive='" + isActive() + '\'' +
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
