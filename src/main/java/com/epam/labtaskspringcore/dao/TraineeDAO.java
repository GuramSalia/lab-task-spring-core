package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.User;

import java.util.List;
import java.util.Map;

public interface TraineeDAO {
    public void create(Trainee trainee);
    public void update(Trainee trainee);
    public void delete(int id);
    public Trainee getById(int id);
    List<Trainee> getTrainees();
}
