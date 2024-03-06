package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.model.Training;
import com.epam.labtaskspringcore.repository.TrainingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("TRAINING_DATABASE")
public class TrainingDAODatabaseImpl implements TrainingDAO{

    TrainingRepository trainingRepository;

    public TrainingDAODatabaseImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }


    @Override
    public Optional<Training> create(Training training) {
        return Optional.of(trainingRepository.save(training));
    }

    @Override
    public Optional<Training> getById(int id) {
        return trainingRepository.findById(id);
    }

    @Override
    public List<Training> getTrainings() {
        return trainingRepository.findAll();
    }
}
