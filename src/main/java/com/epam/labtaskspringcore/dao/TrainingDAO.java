package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.model.Trainer;
import com.epam.labtaskspringcore.model.Training;

import java.util.Optional;

public interface TrainingDAO {
    public Optional<Training> create(Training training);
    public Optional<Training> getById(int id);
}
