package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.model.Trainee;
import com.epam.labtaskspringcore.model.User;
import org.springframework.expression.spel.ast.OperatorBetween;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TraineeDAO {
    public Optional<Trainee> create(Trainee trainee);
    public Optional<Trainee> update(Trainee trainee);
    public boolean delete(int id);
    public Optional<Trainee>  getById(int id);
    List<Trainee> getTrainees();
}
