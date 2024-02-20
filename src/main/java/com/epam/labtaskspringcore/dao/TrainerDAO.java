package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.User;

import java.util.List;

public interface TrainerDAO {
    public void create(Trainer trainer);
    public void update(Trainer trainer);
    public Trainer getById(int id);
    List<User> getTrainers();
}
