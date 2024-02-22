package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.model.Trainee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TraineeDAOImpl implements TraineeDAO {
    private final Map<Integer, Trainee> trainees;

    public TraineeDAOImpl(InMemoryStorage storage) {this.trainees = storage.getTrainees();}

    public void create(Trainee trainee) {trainees.put(trainee.getId(), trainee);}

    public void update(Trainee trainee) {trainees.put(trainee.getId(), trainee);}

    public void delete(int id) {trainees.remove(id);}

    public Trainee getById(int id) {return trainees.get(id);}

    @Override
    public List<Trainee> getTrainees() {return new ArrayList<>(trainees.values());}
}
