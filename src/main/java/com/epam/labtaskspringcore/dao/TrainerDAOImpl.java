package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TrainerDAOImpl implements TrainerDAO {
    private final Map<Integer, Trainer> trainers;

    public TrainerDAOImpl(InMemoryStorage storage) {this.trainers = storage.getTrainers();}

    @Override
    public void create(Trainer trainer) {trainers.put(trainer.getId(), trainer);}

    @Override
    public void update(Trainer trainer) {trainers.put(trainer.getId(), trainer);}

    @Override
    public Trainer getById(int id) {return trainers.get(id);}

    @Override
    public List<User> getTrainers() {return new ArrayList<>(trainers.values());}
}
