package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class TrainerDAOImpl implements TrainerDAO {
    private final Map<Integer, Trainer> trainers;

    public TrainerDAOImpl(InMemoryStorage storage) {this.trainers = storage.getTrainers();}

    @Override
    public Optional<Trainer> create(Trainer trainer) {
        int id = trainer.getUserId();
        if (trainers.containsKey(id)) {
            log.error("Trainer with id {} already exists", id);
            return Optional.empty();
        }
        trainers.put(id, trainer);
        return Optional.of(trainers.get(id));
    }

    @Override
    public Optional<Trainer> update(Trainer trainer) {
        int id = trainer.getUserId();
        if (trainers.containsKey(id)) {
            trainers.put(id, trainer);
            return Optional.of(trainers.get(id));
        } else {
            log.error("Trainer with id {} does not exist", id);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Trainer> getById(int id) {return Optional.ofNullable(trainers.get(id));}

    @Override
    public List<User> getTrainers() {return new ArrayList<>(trainers.values());}
}
