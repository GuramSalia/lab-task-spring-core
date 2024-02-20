package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.Trainer;

public interface TrainerDAO {
    public void create(Trainer trainer);
    public void update(Trainer trainer);
    public Trainer select(int id);
}
