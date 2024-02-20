package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TrainerDAOImpl implements TrainerDAO {
    private final Map<Integer, Trainer> trainers;

    public TrainerDAOImpl(InMemoryStorage storage) {this.trainers = storage.getTrainers();}

    @Override
    public void create(Trainer trainer) {trainers.put(trainer.getTrainerId(), trainer);}
    @Override
    public void update(Trainer trainer) {trainers.put(trainer.getTrainerId(), trainer);}
    @Override
    public Trainer select(int id) {return trainers.get(id);}
}
