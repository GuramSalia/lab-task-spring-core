package com.epam.labtaskspringcore.repository;

import com.epam.labtaskspringcore.model.TrainingType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingTypeRepository extends JpaRepository<TrainingType, Integer> {
        TrainingType findByTrainingType(TrainingType.TrainingTypeEnum trainingType);

}
