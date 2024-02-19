package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.model.Trainee;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public class TraineeDAOImpl implements TraineeDAO {
    private final InMemoryStorage storage;

    public TraineeDAOImpl(InMemoryStorage storage) {this.storage = storage;}

    public List<Trainee> getTrainees() {return storage.getItems(Trainee.class);}
}
