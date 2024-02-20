package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.Training;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TrainingDAOImpl implements TrainingDAO {
    private final Map<Integer, Training> trainings;

    public TrainingDAOImpl(InMemoryStorage storage) {this.trainings = storage.getTrainings();}

    @Override
    public void create(Training training) {trainings.put(training.getTrainingId(), training);}

    @Override
    public Training select(int id) {return trainings.get(id);}
}
