package com.epam.labtaskspringcore.model;

import jakarta.persistence.*;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "TRAINING_TYPES")
@Setter
public class TrainingType {

    public enum TrainingTypeEnum {
        CARDIO, STRENGTH, HIIT, YOGA, PILATES, GROUP, PERSONAL,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRAINING_TYPE_ID")
    private int trainingTypeId;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "TRAINING_TYPE_NAME")
    private TrainingTypeEnum trainingType;


    public TrainingType(){};

    public TrainingType(String trainingType) {
        this.trainingType = TrainingTypeEnum.valueOf(trainingType.toUpperCase());
    }

    @Override
    public String toString() {
        return "TrainingType{" + "trainingType=" + trainingType + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingType that = (TrainingType) o;
        return trainingType == that.trainingType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingType);
    }
}
