package com.epam.labtaskspringcore.repository;

import com.epam.labtaskspringcore.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<Training, Integer> {}
