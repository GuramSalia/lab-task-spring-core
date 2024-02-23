package com.epam.labtaskspringcore.dao;

import com.epam.labtaskspringcore.config.InMemoryStorage;
import com.epam.labtaskspringcore.model.Trainee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class TraineeDAOImpl implements TraineeDAO {
    private final Map<Integer, Trainee> trainees;

    public TraineeDAOImpl(InMemoryStorage storage) {this.trainees = storage.getTrainees();}

    public Optional<Trainee> create(Trainee trainee) {
        if(trainees.containsKey(trainee.getId())){
            log.error("Trainee with id {} already exists", trainee.getId());
            return null;
        } else {
            trainees.put(trainee.getId(), trainee);
            return Optional.of(trainees.get(trainee.getId()));
        }
//        trainees.put(trainee.getId(), trainee);
    }

    public Optional<Trainee> update(Trainee trainee) {
        if(trainees.containsKey(trainee.getId())) {
            trainees.put(trainee.getId(), trainee);
            return Optional.of(trainees.get(trainee.getId()));
        } else {
            log.error("Trainee with id {} does not exist", trainee.getId());
            return null;
        }

//        trainees.put(trainee.getId(), trainee);
    }

    public boolean delete(int id) {
        trainees.remove(id);
        // when connected to DB need to check if deletion was successful in DB.
        return trainees.containsKey(id);
    }

    public Optional<Trainee>  getById(int id) {return Optional.ofNullable(trainees.get(id));}

    @Override
    public List<Trainee> getTrainees() {return new ArrayList<>(trainees.values());}
}
