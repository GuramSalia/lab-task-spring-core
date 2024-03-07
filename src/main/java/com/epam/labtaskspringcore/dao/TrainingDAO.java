package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.model.Training;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface TrainingDAO {
    public Optional<Training> create(Training training);

    public Optional<Training> getById(int id);

    public List<Training> getTrainings();

    public List<Training> getTrainingsByTraineeAndOtherFilters(
            String traineeUsername,
            Date startDate,
            Date endDate,
            String trainerUsername,
            String trainingTypeName);

    List<Training> getTrainingsByTrainerAndOtherFilters(
            String traineeUsername,
            Date startDate,
            Date endDate,
            String trainerUsername);
}
