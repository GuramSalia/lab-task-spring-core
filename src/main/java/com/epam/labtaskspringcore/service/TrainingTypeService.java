package com.epam.labtaskspringcore.service;

import com.epam.labtaskspringcore.model.TrainingType;
import com.epam.labtaskspringcore.repository.TrainingTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingTypeService {
    private final TrainingTypeRepository trainingTypeRepository;

    public TrainingTypeService(TrainingTypeRepository trainingTypeRepository) {this.trainingTypeRepository = trainingTypeRepository;}

    public TrainingType getTrainingType(TrainingType.TrainingTypeEnum trainingType) {

        return trainingTypeRepository.findByTrainingType(trainingType);
    }

    public List<TrainingType> getAllTrainingTypes() {
        return trainingTypeRepository.findAll();
    }
}
