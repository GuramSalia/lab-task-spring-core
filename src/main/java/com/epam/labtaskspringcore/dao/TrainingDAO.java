package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.Training;

public interface TrainingDAO {
    public void create(Training training);
    public Training getById(int id);
}
