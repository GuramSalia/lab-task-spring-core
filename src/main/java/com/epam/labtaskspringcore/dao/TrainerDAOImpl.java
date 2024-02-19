package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.model.Trainer;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public class TrainerDAOImpl implements TrainerDAO {
    private final InMemoryStorage storage;

    public TrainerDAOImpl(InMemoryStorage storage) {this.storage = storage;}

    public List<Trainer> getTrainers() {
        return storage.getItems(Trainer.class);
    }
}
