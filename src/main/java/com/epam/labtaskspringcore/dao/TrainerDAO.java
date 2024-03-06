package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.User;

import java.util.List;
import java.util.Optional;

public interface TrainerDAO {
    public Optional<Trainer> create(Trainer trainer);
    public Optional<Trainer> update(Trainer trainer);
    public Optional<Trainer> getById(int id);
    List<Trainer> getTrainers();
    Optional<Trainer> findByUsername(String username);

    Optional<Trainer> findByUsernameAndPassword(String username, String password);
}
