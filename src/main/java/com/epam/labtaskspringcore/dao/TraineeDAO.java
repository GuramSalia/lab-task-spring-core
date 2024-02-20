package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.model.Trainee;

public interface TraineeDAO {
    public void create(Trainee trainee);

    public void update(Trainee trainee);

    public void delete(int id);

    public Trainee select(int id);
}
