package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.model.Trainee;

import java.util.List;
import java.util.Optional;

public interface TraineeDAO {
    public Optional<Trainee> create(Trainee trainee);

    public Optional<Trainee> update(Trainee trainee);

    public boolean delete(int id);

    public Optional<Trainee> getById(int id);

    List<Trainee> getTrainees();
}
